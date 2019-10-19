package bg.sofia.uni.fmi.mjt.virtualwallet.core.payment;

import java.time.LocalDateTime;

public class PaymentInfo {

    private String reason;
    private String location;
    private double cost;

    public PaymentInfo(String reason, String location, double cost) {
        this.reason = reason;
        this.location = location;
        this.cost = cost;
    }

    public PaymentInfo(String reason, String location, double cost, LocalDateTime time) {
        this(reason, location, cost);
    }

    public double getCost() {
        return this.cost;
    }

    public String getReason() {
        return this.reason;
    }

    public String getLocation() {
        return this.location;
    }
}
