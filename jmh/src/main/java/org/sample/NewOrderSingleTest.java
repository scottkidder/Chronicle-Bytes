/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sample;


import domain.objects.ExecutionReportMessage;
import domain.objects.NewOrderSingleMessage;
import domain.objects.OrderCancelRequestMessage;
import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.util.StringInternerBytes;
import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.network.TCPRegistry;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import software.chronicle.FixServer;
import software.chronicle.message.types.NewOrderSingle;
import software.chronicle.parsers.FixMessageParser;
import software.chronicle.parsers.NewOrderSingleParser;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@State(Scope.Thread)
public class NewOrderSingleTest {

    public static final String HOSTPORT = "host.port";
    final NewOrderSingleParser parser = new NewOrderSingleParser(new StringInternerBytes(1024));
    final NewOrderSingle newOrderSingleMessage = new NewOrderSingleMessage();

    final Bytes gmBytes = Bytes.from(("8=FIX.4" +
            ".2|9=192|35=D|49=CLIENT|56=GSITEST|52=20110315-16:13:31.635|34=460|40=2|" +
            "54=1|55=LCOM1|11=1131/2011-03-15-04:13|21=3|60=20110315-16:13:31|38=1|44=200.0|59=0|" +
            "200=201106|167=FUT|22=5|48=LCOM1|1=ABCTEST1|10=063|").replace('|', '\u0001'));

    public static void main(String[] args) throws RunnerException, InvocationTargetException, IllegalAccessException, IOException {
        if (Jvm.isDebug()) {
            NewOrderSingleTest main = new NewOrderSingleTest();

            for (Method m : NewOrderSingleTest.class.getMethods()) {
                if (m.getAnnotation(Benchmark.class) != null) {
                    m.invoke(main);
                }
            }

        } else {
            int time = Boolean.getBoolean("longTest") ? 30 : 2;
            System.out.println("measurementTime: " + time + " secs");
            Options opt = new OptionsBuilder()
                    .include(NewOrderSingleTest.class.getSimpleName())
                    .warmupIterations(5)
//                .measurementIterations(5)
                    .forks(1)
                    .mode(Mode.SampleTime)
                    .measurementTime(TimeValue.seconds(time))
                    .timeUnit(TimeUnit.NANOSECONDS)
                    .build();

            new Runner(opt).run();
        }
    }

    @Benchmark
    public boolean test() {
        gmBytes.readPosition(0);
        gmBytes.readLimit(gmBytes.writePosition());

        final FixMessageParser fixMessageParser = new FixMessageParser(
                () -> newOrderSingleMessage,
                () -> null,
                () -> null,
                () -> null,
                () -> null,
                () -> null,
                () -> null);

        return fixMessageParser.parse(gmBytes, null, 0);

    }


    @Test
    public void testNewOrderSingle() throws IOException, InterruptedException, ParseException {
        TCPRegistry.createServerSocketChannelFor(HOSTPORT);
        CountDownLatch latch = new CountDownLatch(1);
        NewOrderSingleMessage actual = new NewOrderSingleMessage(latch);
        final FixMessageParser fixMessageParser = new FixMessageParser(
                () -> actual,
                () -> null,
                () -> new ExecutionReportMessage(),
                () -> new OrderCancelRequestMessage(),
                () -> null,
                () -> null,
                () -> null);

        new FixServer(HOSTPORT, fixMessageParser, null).start();

        SocketChannel result = TCPRegistry.createSocketChannel(HOSTPORT);
        int tcpBufferSize = 2 << 20;
        Socket socket = result.socket();
        socket.setTcpNoDelay(true);
        socket.setReceiveBufferSize(tcpBufferSize);
        socket.setSendBufferSize(tcpBufferSize);
        result.configureBlocking(false);
        System.out.println("successfully connected");

        ByteBuffer buff = (ByteBuffer) (gmBytes.underlyingObject());
        assert buff != null;
        buff.clear();

        while (buff.remaining() > 0) {
            result.write(buff);
        }

        latch.await(100, TimeUnit.MINUTES);

        final NewOrderSingleMessage expected = new NewOrderSingleMessage();
        expected.clOrdID("1131/2011-03-15-04:13");
        expected.symbol("LCOM1");
        expected.securityID("LCOM1");
        expected.orderQty(1);
        expected.securityType("FUT");
        expected.timeInForce('0');
        expected.handlInst('3');
        expected.account("ABCTEST1");
        expected.price(200.0);
        expected.ordType('2');
        expected.side('1');
        expected.sendingTime(timeStampToLong("20110315-16:13:31"));
        expected.transactTime(timeStampToLong("20110315-16:13:31"));

        Assert.assertEquals(expected, actual);
    }


    public static long timeStampToLong(String timeStamp) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd-HH:mm:ss").parse(timeStamp).getTime();
    }


}
