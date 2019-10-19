package bg.sofia.uni.fmi.mjt.virtualwallet.core.payment;

import java.time.LocalDateTime;

public class TransactionInfo implements Comparable<TransactionInfo> {

    private String cardName;
    private PaymentInfo paymentInfo;
    private LocalDateTime time;

    public TransactionInfo(String cardName, PaymentInfo paymentInfo) {
        this.cardName = cardName;
        this.paymentInfo = paymentInfo;
        this.time = LocalDateTime.now();
    }

    public TransactionInfo(String cardName, PaymentInfo paymentInfo, LocalDateTime time) {
        this(cardName, paymentInfo);
        this.time = time;
    }

    @Override
    public int compareTo(TransactionInfo other) {
        return this.time.compareTo(other.time);
    }
}
