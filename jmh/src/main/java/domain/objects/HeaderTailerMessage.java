package domain.objects;


import org.jetbrains.annotations.NotNull;
import software.chronicle.message.types.StandardHeader;
import software.chronicle.message.types.StandardTailer;

import java.util.concurrent.CountDownLatch;

/**
 * @author Rob Austin.
 */
public class HeaderTailerMessage<T extends CharSequence> implements StandardHeader<T>,
        StandardTailer {

    private char msgType;

    public HeaderTailerMessage() {
        this.latch = null;
    }


    public HeaderTailerMessage(@NotNull final CountDownLatch latch) {
        this.latch = latch;
    }

    private final CountDownLatch latch;
    private T senderCompID;
    private T targetCompID;
    private long msgSeqNum;
    private long checkSum;
    private long sendingTime;

    public long checkSum() {
        return checkSum;
    }

    public long msgSeqNum() {
        return msgSeqNum;
    }

    public T targetCompID() {
        return targetCompID;
    }

    public T senderCompID() {
        return senderCompID;
    }

    public long sendingTime() {
        return sendingTime;
    }

    @Override
    public void senderCompID(T senderCompID) {
        this.senderCompID = senderCompID;
    }

    @Override
    public void targetCompID(T targetCompID) {
        this.targetCompID = targetCompID;
    }

    @Override
    public void msgSeqNum(long msgSeqNum) {
        this.msgSeqNum = msgSeqNum;
    }

    @Override
    public void sendingTime(long utcTimestamp) {
        this.sendingTime = utcTimestamp;
    }

    @Override
    public void msgType(char msgType) {
        this.msgType = msgType;
    }


    @Override
    public void checkSum(long checkSum) {
        this.checkSum = checkSum;
        if (latch != null)
            latch.countDown();
    }

    @Override
    public String toString() {
        return "HeaderTailerMessage{" +
                "msgType=" + msgType +
                ", latch=" + latch +
                ", senderCompID=" + senderCompID +
                ", targetCompID=" + targetCompID +
                ", msgSeqNum=" + msgSeqNum +
                ", checkSum=" + checkSum +
                ", sendingTime=" + sendingTime +
                '}';
    }
}


