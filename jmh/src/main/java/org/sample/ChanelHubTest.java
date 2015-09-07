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
import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.network.TCPRegistry;
import net.openhft.chronicle.network.connection.TcpChannelHub;
import net.openhft.chronicle.threads.EventGroup;
import net.openhft.chronicle.wire.Wire;
import net.openhft.chronicle.wire.WireType;
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
import software.chronicle.parsers.FixMessageParser;

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
public class ChanelHubTest {

    public static final WireType WIRE_TYPE = WireType.BINARY;
    public static final String HOSTPORT = "host.port";
    final Wire wire = WIRE_TYPE.apply(Bytes.elasticByteBuffer());
    private TcpChannelHub tcpChannelHub;
    private EventGroup eg;
    private String expectedMessage;

    public static void main(String[] args) throws RunnerException, InvocationTargetException, IllegalAccessException, IOException {
        if (Jvm.isDebug()) {
            ChanelHubTest main = new ChanelHubTest();

            for (Method m : ChanelHubTest.class.getMethods()) {
                if (m.getAnnotation(Benchmark.class) != null) {
                    m.invoke(main);
                }
            }

        } else {
            int time = Boolean.getBoolean("longTest") ? 30 : 2;
            System.out.println("measurementTime: " + time + " secs");
            Options opt = new OptionsBuilder()
                    .include(ChanelHubTest.class.getSimpleName())
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

    @Test
    public void testNyseFullFillExecutionReport() throws IOException, InterruptedException, ParseException {

        final Bytes gmBytes = Bytes.from(("8=FIX.4" +
                ".2|9=192|35=D|49=CLIENT|56=GSITEST|52=20110315-16:13:31.635|34=460|40=2|" +
                "54=1|55=LCOM1|11=1131/2011-03-15-04:13|21=3|60=20110315-16:13:31|38=1|44=200.0|59=0|" +
                "200=201106|167=FUT|22=5|48=LCOM1|1=ABCTEST1|10=063|").replace('|', '\u0001'));


        TCPRegistry.createServerSocketChannelFor(HOSTPORT);
        CountDownLatch latch = new CountDownLatch(1);
        ExecutionReportMessage actual = new ExecutionReportMessage(latch);

        final FixMessageParser fixMessageParser = new FixMessageParser(
                () -> new NewOrderSingleMessage(),
                () -> null,
                () -> actual,
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

        final ExecutionReportMessage expected = new ExecutionReportMessage();

        expected.avgPx(0);
        expected.checkSum(80);
        expected.cumQty(0);
        expected.execID("NF 0542/03232009 001001001");
        expected.execTransType('0');
        expected.msgSeqNum(5);
        expected.orderID("NF 0542/03232009");
        expected.rule80A('A');
        expected.senderCompID("CCG");
        expected.sendingTime(timeStampToLong("20090323-15:40:35"));
        expected.side('1');
        expected.symbol("CVS");
        expected.targetCompID("ABC_DEFG01");
        expected.text("Fill");
        expected.clOrdID("NF 0542/03232009");
        expected.lastCapacity('1');
        expected.lastMkt("N");
        expected.lastPx(25.4800);
        expected.lastShares(100);
        expected.execType('2');
        expected.orderQty(100);
        expected.ordStatus('2');
        expected.ordType('1');
        expected.timeInForce('0');
        expected.transactTime(timeStampToLong("20090323-15:40:30"));
        expected.settlmntTyp('0');
        expected.execBroker("0034");
        expected.deliverToCompID("XYZ");
        expected.execType('2');
        expected.leavesQty(0);
        expected.securityExchange("N");
        expected.contraTrader("0000");
        expected.contraBroker("TOD");
        expected.noContraBrokers(1);
        expected.contraTradeQty(100);
        expected.contraTradeTime(1243);
        //expected.billingRate('1');
        // expected.nyseDirect();
        //   expected.ExecutionInformation();
        //   expected.ERCReferenceNumber();
        //  expected.DBKLinkID();
        //expected.BillingIndicator();
        //expected.ExpERCReferenceNumber();

        Assert.assertEquals(expected, actual);


    }


    public static long timeStampToLong(String timeStamp) throws ParseException {
        return new SimpleDateFormat("yyyyMMdd-HH:mm:ss").parse(timeStamp).getTime();
    }


}
