package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class GoldenCard extends Card {

    private static final double CASH_BACK_PERCENTAGE = 0.15;

    public GoldenCard(String card) {
        super(card);
    }

    @Override
    public boolean executePayment(double cost) {
        double amountToPay = cost * (1 - CASH_BACK_PERCENTAGE);
        double newBalance = this.getAmount() - amountToPay;

        if (amountToPay < 0 || newBalance < 0) {
            return false;
        }

        this.setAmount(newBalance);
        return true;
    }
}
