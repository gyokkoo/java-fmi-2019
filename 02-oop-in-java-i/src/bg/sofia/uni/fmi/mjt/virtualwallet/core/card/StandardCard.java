package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public class StandardCard extends Card {

    public StandardCard(String card) {
        super(card);
    }

    @Override
    public boolean executePayment(double cost) {
        if (cost < 0 || this.getAmount() - cost < 0) {
            return false;
        }

        this.setAmount(this.getAmount() - cost);
        return true;
    }
}
