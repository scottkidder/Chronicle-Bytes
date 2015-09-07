package domain.objects;

import org.jetbrains.annotations.NotNull;
import software.chronicle.message.types.ExecutionReport;

import java.util.concurrent.CountDownLatch;

/**
 * @author Rob Austin.
 */
public class ExecutionReportMessage extends HeaderTailerMessage<String> implements
        ExecutionReport<String> {

    private double avgPx;
    private double cumQty;
    private String execID = "";
    private char execTransType;
    private char execType;
    private long leavesQty;
    private char ordStatus;
    private String orderId = "";
    private long UTCTimestamp;
    private String symbol = "";
    private char side;
    private char rule80A;
    private String text = "";
    private long orderQty;
    private char ordType;
    private long lastShares;
    private double lastPx;
    private String lastMkt = "";
    private char lastCapacity;
    private String clOrdID = "";
    private String deliverToCompID;
    private char timeInForce;
    private long transactTime;
    private String execBroker = "";
    private String securityExchange;
    private long noContraBrokers;
    private String contraBroker = "";
    private String contraTrader = "";
    private long contraTradeTime;
    private long contraTradeQty;
    private char settlmntTyp;
    private String idSource = "";


    public ExecutionReportMessage() {
    }

    public ExecutionReportMessage(@NotNull final CountDownLatch latch) {
        super(latch);
    }

    @Override
    public void avgPx(double avgPx) {
        this.avgPx = avgPx;
    }

    @Override
    public void cumQty(long cumQty) {
        this.cumQty = cumQty;
    }

    /**
     * @param execID Unique identifier of execution message as assigned by broker (will be 0 (zero)
     *               for ExecTransType=3 (Status)). Uniqueness must be guaranteed within a single
     *               trading day or the life of a multi-day order. Firms which accept multi-day
     *               orders should consider embedding a date within the ExecID field to assure
     */
    @Override
    public void execID(String execID) {
        this.execID = execID;
    }

    @Override
    public void execTransType(char execTransType) {
        this.execTransType = execTransType;
    }

    @Override
    public void execType(char execType) {
        this.execType = execType;
    }

    @Override
    public void leavesQty(long leavesQty) {
        this.leavesQty = leavesQty;
    }

    @Override
    public void ordStatus(char ordStatus) {
        this.ordStatus = ordStatus;
    }

    @Override
    public void orderID(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void side(char side) {
        this.side = side;
    }

    @Override
    public void sendingTime(long UTCTimestamp) {
        this.UTCTimestamp = UTCTimestamp;
    }


    @Override
    public void symbol(String symbol) {
        this.symbol = symbol;
    }

    public double avgPx() {
        return avgPx;
    }

    public double cumQty() {
        return cumQty;
    }

    public String execID() {
        return execID;
    }

    public char execTransType() {
        return execTransType;
    }

    public char execType() {
        return execType;
    }

    public long leavesQty() {
        return leavesQty;
    }

    public char ordStatus() {
        return ordStatus;
    }

    public String orderId() {
        return orderId;
    }

    public long UTCTimestamp() {
        return UTCTimestamp;
    }

    public String symbol() {
        return symbol;
    }

    public char side() {
        return side;
    }

    @Override
    public void rule80A(char rule80A) {
        this.rule80A = rule80A;
    }

    @Override
    public void clOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    @Override
    public void lastCapacity(char lastCapacity) {
        this.lastCapacity = lastCapacity;
    }

    @Override
    public void lastMkt(String lastMkt) {
        this.lastMkt = lastMkt;
    }

    @Override
    public void lastPx(double lastPx) {
        this.lastPx = lastPx;
    }

    @Override
    public void lastShares(long lastShares) {
        this.lastShares = lastShares;
    }

    @Override
    public void ordType(char ordType) {
        this.ordType = ordType;
    }

    @Override
    public void orderQty(long orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public void text(String text) {
        this.text = text;
    }

    @Override
    public void deliverToCompID(String deliverToCompID) {
        this.deliverToCompID = deliverToCompID;
    }

    @Override
    public void timeInForce(char timeInForce) {
        this.timeInForce = timeInForce;
    }

    @Override
    public void transactTime(long transactTime) {
        this.transactTime = transactTime;
    }

    @Override
    public void execBroker(String execBroker) {
        this.execBroker = execBroker;
    }

    @Override
    public void securityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    @Override
    public void noContraBrokers(long noContraBrokers) {
        this.noContraBrokers = noContraBrokers;
    }

    @Override
    public void contraBroker(String contraBroker) {
        this.contraBroker = contraBroker;
    }

    @Override
    public void contraTrader(String contraTrader) {
        this.contraTrader = contraTrader;
    }

    @Override
    public void contraTradeQty(long contraTradeQty) {
        this.contraTradeQty = contraTradeQty;
    }

    @Override
    public void contraTradeTime(long contraTradeTime) {
        this.contraTradeTime = contraTradeTime;
    }

    @Override
    public void settlmntTyp(char settlmntTyp) {
        this.settlmntTyp = settlmntTyp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExecutionReportMessage)) return false;

        ExecutionReportMessage that = (ExecutionReportMessage) o;

        if (Double.compare(that.avgPx, avgPx) != 0) return false;
        if (Double.compare(that.cumQty, cumQty) != 0) return false;
        if (execTransType != that.execTransType) return false;
        if (execType != that.execType) return false;
        if (leavesQty != that.leavesQty) return false;
        if (ordStatus != that.ordStatus) return false;
        if (UTCTimestamp != that.UTCTimestamp) return false;
        if (side != that.side) return false;
        if (rule80A != that.rule80A) return false;
        if (orderQty != that.orderQty) return false;
        if (ordType != that.ordType) return false;
        if (lastShares != that.lastShares) return false;
        if (Double.compare(that.lastPx, lastPx) != 0) return false;
        if (lastCapacity != that.lastCapacity) return false;
        if (timeInForce != that.timeInForce) return false;
        if (transactTime != that.transactTime) return false;
        if (noContraBrokers != that.noContraBrokers) return false;
        if (contraTradeTime != that.contraTradeTime) return false;
        if (contraTradeQty != that.contraTradeQty) return false;
        if (settlmntTyp != that.settlmntTyp) return false;
        if (!execID.equals(that.execID)) return false;
        if (!orderId.equals(that.orderId)) return false;
        if (!symbol.equals(that.symbol)) return false;
        if (!text.equals(that.text)) return false;
        if (!lastMkt.equals(that.lastMkt)) return false;
        if (!clOrdID.equals(that.clOrdID)) return false;
        if (!deliverToCompID.equals(that.deliverToCompID)) return false;
        if (!execBroker.equals(that.execBroker)) return false;
        if (!securityExchange.equals(that.securityExchange)) return false;
        if (!contraBroker.equals(that.contraBroker)) return false;
        return contraTrader.equals(that.contraTrader);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(avgPx);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(cumQty);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + execID.hashCode();
        result = 31 * result + (int) execTransType;
        result = 31 * result + (int) execType;
        result = 31 * result + (int) (leavesQty ^ (leavesQty >>> 32));
        result = 31 * result + (int) ordStatus;
        result = 31 * result + orderId.hashCode();
        result = 31 * result + (int) (UTCTimestamp ^ (UTCTimestamp >>> 32));
        result = 31 * result + symbol.hashCode();
        result = 31 * result + (int) side;
        result = 31 * result + (int) rule80A;
        result = 31 * result + text.hashCode();
        result = 31 * result + (int) (orderQty ^ (orderQty >>> 32));
        result = 31 * result + (int) ordType;
        result = 31 * result + (int) (lastShares ^ (lastShares >>> 32));
        temp = Double.doubleToLongBits(lastPx);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + lastMkt.hashCode();
        result = 31 * result + (int) lastCapacity;
        result = 31 * result + clOrdID.hashCode();
        result = 31 * result + deliverToCompID.hashCode();
        result = 31 * result + (int) timeInForce;
        result = 31 * result + (int) (transactTime ^ (transactTime >>> 32));
        result = 31 * result + execBroker.hashCode();
        result = 31 * result + securityExchange.hashCode();
        result = 31 * result + (int) (noContraBrokers ^ (noContraBrokers >>> 32));
        result = 31 * result + contraBroker.hashCode();
        result = 31 * result + contraTrader.hashCode();
        result = 31 * result + (int) (contraTradeTime ^ (contraTradeTime >>> 32));
        result = 31 * result + (int) (contraTradeQty ^ (contraTradeQty >>> 32));
        result = 31 * result + (int) settlmntTyp;
        return result;
    }

    @Override
    public String toString() {
        return "ExecutionReportMessage{" +
                "avgPx=" + avgPx +
                ", cumQty=" + cumQty +
                ", execID='" + execID + '\'' +
                ", execTransType=" + execTransType +
                ", execType=" + execType +
                ", leavesQty=" + leavesQty +
                ", ordStatus=" + ordStatus +
                ", orderId='" + orderId + '\'' +
                ", UTCTimestamp=" + UTCTimestamp +
                ", symbol='" + symbol + '\'' +
                ", side=" + side +
                ", rule80A=" + rule80A +
                ", text='" + text + '\'' +
                ", orderQty=" + orderQty +
                ", ordType=" + ordType +
                ", lastShares=" + lastShares +
                ", lastPx=" + lastPx +
                ", lastMkt='" + lastMkt + '\'' +
                ", lastCapacity=" + lastCapacity +
                ", clOrdID='" + clOrdID + '\'' +
                ", deliverToCompID='" + deliverToCompID + '\'' +
                ", timeInForce=" + timeInForce +
                ", transactTime=" + transactTime +
                ", execBroker='" + execBroker + '\'' +
                ", securityExchange='" + securityExchange + '\'' +
                ", noContraBrokers=" + noContraBrokers +
                ", contraBroker='" + contraBroker + '\'' +
                ", contraTrader='" + contraTrader + '\'' +
                ", contraTradeTime=" + contraTradeTime +
                ", contraTradeQty=" + contraTradeQty +
                ", settlmntTyp=" + settlmntTyp +
                '}';
    }


}
