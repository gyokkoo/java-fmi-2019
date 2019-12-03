package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class PremiumOffer extends RegularOffer implements Offer {

    private static final int MAX_DISCOUNT = 100;

    private double discount;

    public PremiumOffer(String productName,
                        LocalDate date,
                        String description,
                        double price,
                        double shippingPrice,
                        double discount) {
        super(productName, date, description, price, shippingPrice);
        this.setDiscount(discount);
    }

    private void setDiscount(double discount) {
        if (discount < 0) {
            discount = 0;
        } else if (discount > MAX_DISCOUNT) {
            discount = MAX_DISCOUNT;
        }

        this.discount = Double.parseDouble(String.format("%.2f", discount));
    }

    @Override
    public double getTotalPrice() {
        return (getPrice() + getShippingPrice()) * (1 - discount);
    }
}
