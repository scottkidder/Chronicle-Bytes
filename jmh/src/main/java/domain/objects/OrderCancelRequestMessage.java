package domain.objects;

import software.chronicle.message.types.OrderCancelRequest;

import java.util.concurrent.CountDownLatch;

/**
 * @author Rob Austin.
 */
public class OrderCancelRequestMessage extends HeaderTailerMessage<String> implements
        OrderCancelRequest<String> {
    private String clOrdID;
    private String orderId;
    private String origClOrdID;
    private char side;
    private String senderCompID;
    private String targetCompID;
    private long msgSeqNum;
    private long sendingTime;
    private long checkSum;
    private String symbol;
    private long transactTime;

    public OrderCancelRequestMessage(CountDownLatch latch) {
        super(latch);
    }

    public OrderCancelRequestMessage() {
    }

    @Override
    public void clOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    @Override
    public void orderID(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void origClOrdID(String origClOrdID) {
        this.origClOrdID = origClOrdID;
    }

    @Override
    public void side(char side) {
        this.side = side;
    }

    @Override
    public void senderCompID(String senderCompID) {
        this.senderCompID = senderCompID;
    }

    @Override
    public void targetCompID(String targetCompID) {
        this.targetCompID = targetCompID;
    }

    @Override
    public void msgSeqNum(long msgSeqNum) {
        this.msgSeqNum = msgSeqNum;
    }

    @Override
    public void sendingTime(long UTCTimestamp) {
        this.sendingTime = sendingTime;
    }


    @Override
    public void symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void transactTime(long transactTime) {
        this.transactTime = transactTime;
    }

    public String clOrdID() {
        return clOrdID;
    }

    public String orderId() {
        return orderId;
    }

    public String origClOrdID() {
        return origClOrdID;
    }

    public char side() {
        return side;
    }

    public String senderCompID() {
        return senderCompID;
    }

    public String targetCompID() {
        return targetCompID;
    }

    public long msgSeqNum() {
        return msgSeqNum;
    }

    public long sendingTime() {
        return sendingTime;
    }


    public String symbol() {
        return symbol;
    }

    public long transactTime() {
        return transactTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderCancelRequestMessage)) return false;

        OrderCancelRequestMessage that = (OrderCancelRequestMessage) o;

        if (side != that.side) return false;
        if (msgSeqNum != that.msgSeqNum) return false;
        if (sendingTime != that.sendingTime) return false;
        if (checkSum != that.checkSum) return false;
        if (transactTime != that.transactTime) return false;
        if (!clOrdID.equals(that.clOrdID)) return false;
        if (!orderId.equals(that.orderId)) return false;
        if (!origClOrdID.equals(that.origClOrdID)) return false;
        if (!senderCompID.equals(that.senderCompID)) return false;
        if (!targetCompID.equals(that.targetCompID)) return false;
        return symbol.equals(that.symbol);

    }

    @Override
    public int hashCode() {
        int result = clOrdID.hashCode();
        result = 31 * result + orderId.hashCode();
        result = 31 * result + origClOrdID.hashCode();
        result = 31 * result + (int) side;
        result = 31 * result + senderCompID.hashCode();
        result = 31 * result + targetCompID.hashCode();
        result = 31 * result + (int) (msgSeqNum ^ (msgSeqNum >>> 32));
        result = 31 * result + (int) (sendingTime ^ (sendingTime >>> 32));
        result = 31 * result + (int) (checkSum ^ (checkSum >>> 32));
        result = 31 * result + symbol.hashCode();
        result = 31 * result + (int) (transactTime ^ (transactTime >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OrderCancelRequestMessage{" +
                "clOrdID='" + clOrdID + '\'' +
                ", orderId='" + orderId + '\'' +
                ", origClOrdID='" + origClOrdID + '\'' +
                ", side=" + side +
                ", senderCompID='" + senderCompID + '\'' +
                ", targetCompID='" + targetCompID + '\'' +
                ", msgSeqNum=" + msgSeqNum +
                ", sendingTime=" + sendingTime +
                ", checkSum=" + checkSum +
                ", symbol='" + symbol + '\'' +
                ", transactTime=" + transactTime +
                '}';
    }
}
