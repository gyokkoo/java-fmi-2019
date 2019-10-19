package bg.sofia.uni.fmi.mjt.virtualwallet.core.card;

public abstract class Card {

    private String name;
    private double amount;

    public Card(String name) {
        this.name = name;
        this.amount = 0;
    }

    public abstract boolean executePayment(double cost);

    public String getName() {
        return this.name;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount, cannot be negative!");
        }

        this.amount = amount;
    }
}
