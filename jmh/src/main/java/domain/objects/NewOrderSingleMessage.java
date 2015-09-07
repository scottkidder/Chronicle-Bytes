package domain.objects;


import software.chronicle.message.types.NewOrderSingle;

import java.util.concurrent.CountDownLatch;

/**
 * @author Rob Austin.
 */
public class NewOrderSingleMessage extends HeaderTailerMessage<String> implements
        NewOrderSingle<String> {

    private String clOrdID = "";
    private String symbol = "";
    private String securityID = "";
    private long orderQty;
    private String securityType = "";
    private char side;
    private char timeInForce;
    private long handlInst;
    private String account = "";
    private double price;
    private char ordType;
    private char rule80A;
    private long sendingTime;
    private long transactTime;
    private String onBehalfOfCompID = "";
    private String securityExchange = "";
    private String maturityMonthYear = "";
    private String idSource = "";


    public NewOrderSingleMessage() {
        super();
    }

    public NewOrderSingleMessage(CountDownLatch latch) {
        super(latch);
    }

    public double price() {
        return price;
    }

    public String account() {
        return account;
    }

    public long handlInst() {
        return handlInst;
    }

    public long timeInForce() {
        return timeInForce;
    }

    public char side() {
        return side;
    }

    public String securityType() {
        return securityType;
    }

    public long orderQty() {
        return orderQty;
    }

    public String securityID() {
        return securityID;
    }

    public String symbol() {
        return symbol;
    }

    public String clOrdID() {
        return clOrdID;
    }

    private long checkSum;

    @Override
    public void clOrdID(String clOrdID) {
        this.clOrdID = clOrdID;
    }

    @Override
    public void symbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void securityID(String securityID) {
        this.securityID = securityID;
    }

    @Override
    public void orderQty(long orderQty) {
        this.orderQty = orderQty;
    }

    @Override
    public void securityType(String securityType) {
        this.securityType = securityType;
    }

    @Override
    public void side(char side) {
        this.side = side;
    }

    @Override
    public void timeInForce(char timeInForce) {
        this.timeInForce = timeInForce;
    }

    @Override
    public void handlInst(char handlInst) {
        this.handlInst = handlInst;
    }

    @Override
    public void account(String account) {
        this.account = account;
    }

    @Override
    public void price(double price) {
        this.price = price;
    }

    @Override
    public void ordType(char ordType) {
        this.ordType = ordType;
    }

    @Override
    public void rule80A(char rule80A) {
        this.rule80A = rule80A;
    }

    @Override
    public void sendingTime(long sendingTime) {
        this.sendingTime = sendingTime;
    }

    @Override
    public void transactTime(long transactTime) {
        this.transactTime = transactTime;
    }

    @Override
    public void onBehalfOfCompID(String onBehalfOfCompID) {
        this.onBehalfOfCompID = onBehalfOfCompID;
    }

    @Override
    public void securityExchange(String securityExchange) {
        this.securityExchange = securityExchange;
    }

    public char ordType() {
        return ordType;
    }

    public char rule80A() {
        return rule80A;
    }

    @Override
    public long sendingTime() {
        return sendingTime;
    }

    public long transactTime() {
        return transactTime;
    }

    public String onBehalfOfCompID() {
        return onBehalfOfCompID;
    }

    public String securityExchange() {
        return securityExchange;
    }


    @Override
    public void maturityMonthYear(String s) {
        this.maturityMonthYear = maturityMonthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewOrderSingleMessage)) return false;

        NewOrderSingleMessage that = (NewOrderSingleMessage) o;

        if (orderQty != that.orderQty) return false;
        if (side != that.side) return false;
        if (timeInForce != that.timeInForce) return false;
        if (handlInst != that.handlInst) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (ordType != that.ordType) return false;
        if (rule80A != that.rule80A) return false;
        if (sendingTime != that.sendingTime) return false;
        if (transactTime != that.transactTime) return false;
        if (checkSum != that.checkSum) return false;
        if (clOrdID != null ? !clOrdID.equals(that.clOrdID) : that.clOrdID != null) return false;
        if (symbol != null ? !symbol.equals(that.symbol) : that.symbol != null) return false;
        if (securityID != null ? !securityID.equals(that.securityID) : that.securityID != null)
            return false;
        if (securityType != null ? !securityType.equals(that.securityType) : that.securityType != null)
            return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (onBehalfOfCompID != null ? !onBehalfOfCompID.equals(that.onBehalfOfCompID) : that.onBehalfOfCompID != null)
            return false;
        return !(securityExchange != null ? !securityExchange.equals(that.securityExchange) : that.securityExchange != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = clOrdID != null ? clOrdID.hashCode() : 0;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        result = 31 * result + (securityID != null ? securityID.hashCode() : 0);
        result = 31 * result + (int) (orderQty ^ (orderQty >>> 32));
        result = 31 * result + (securityType != null ? securityType.hashCode() : 0);
        result = 31 * result + (int) side;
        result = 31 * result + (int) (timeInForce ^ (timeInForce >>> 32));
        result = 31 * result + (int) (handlInst ^ (handlInst >>> 32));
        result = 31 * result + (account != null ? account.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (int) ordType;
        result = 31 * result + (int) rule80A;
        result = 31 * result + (int) (sendingTime ^ (sendingTime >>> 32));
        result = 31 * result + (int) (transactTime ^ (transactTime >>> 32));
        result = 31 * result + (onBehalfOfCompID != null ? onBehalfOfCompID.hashCode() : 0);
        result = 31 * result + (securityExchange != null ? securityExchange.hashCode() : 0);
        result = 31 * result + (int) (checkSum ^ (checkSum >>> 32));
        return result;
    }


    @Override
    public String toString() {
        return "OrderSingleMessage{" +
                "clOrdID='" + clOrdID + '\'' +
                ", symbol='" + symbol + '\'' +
                ", securityID='" + securityID + '\'' +
                ", orderQty=" + orderQty +
                ", securityType='" + securityType + '\'' +
                ", side=" + side +
                ", timeInForce=" + timeInForce +
                ", handlInst=" + handlInst +
                ", account='" + account + '\'' +
                ", price=" + price +
                ", ordType=" + ordType +
                ", rule80A=" + rule80A +
                ", sendingTime=" + sendingTime +
                ", transactTime=" + transactTime +
                ", onBehalfOfCompID='" + onBehalfOfCompID + '\'' +
                ", securityExchange='" + securityExchange + '\'' +
                ", checkSum=" + checkSum +
                '}';
    }


    @Override
    public void idSource(String idSource) {
        this.idSource = idSource;
    }
}
