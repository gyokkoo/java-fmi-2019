package bg.sofia.uni.fmi.mjt.shopping.portal.offer;

import java.time.LocalDate;

public class RegularOffer implements Offer {

    private String productName;
    private LocalDate date;
    private String description;
    private double price;
    private double shippingPrice;

    public RegularOffer(String productName, LocalDate date, String description, double price, double shippingPrice) {
        this.productName = productName;
        this.date = date;
        this.description = description;
        this.price = price;
        this.shippingPrice = shippingPrice;
    }

    @Override
    public String getProductName() {
        return productName;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public double getShippingPrice() {
        return shippingPrice;
    }

    @Override
    public double getTotalPrice() {
        return price + shippingPrice;
    }

    @Override
    public int hashCode() {
        return productName.hashCode() * date.hashCode() + (int) (getTotalPrice());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Offer)) {
            return false;
        }

        Offer otherOffer = (Offer) obj;

        return this.getProductName().toLowerCase().equals((otherOffer).getProductName().toLowerCase()) &&
                this.getDate().equals(otherOffer.getDate()) &&
                this.getTotalPrice() == otherOffer.getTotalPrice();
    }
}
