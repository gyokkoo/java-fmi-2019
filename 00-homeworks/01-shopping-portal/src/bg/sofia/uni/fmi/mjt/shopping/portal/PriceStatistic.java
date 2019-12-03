package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.util.Collection;

public class PriceStatistic {

    private LocalDate time;
    private Collection<Offer> productOffers;

    PriceStatistic(LocalDate time, Collection<Offer> productOffers) {
        this.time = time;
        this.productOffers = productOffers;
    }

    /**
     * Returns the date for which the statistic is
     * collected.
     */
    public LocalDate getDate() {
        return time;
    }

    /**
     * Returns the lowest total price from the offers
     * for this product for the specific date.
     */
    public double getLowestPrice() {
        double lowestPrice = Double.MAX_VALUE;
        for (Offer offer : productOffers) {
            if (offer.getTotalPrice() < lowestPrice) {
                lowestPrice = offer.getTotalPrice();
            }
        }

        return lowestPrice;
    }

    /**
     * Return the average total price from the offers
     * for this product for the specific date.
     */
    public double getAveragePrice() {
        double sum = 0;
        for (Offer offer : productOffers) {
            sum += offer.getTotalPrice();
        }

        return sum / productOffers.size();
    }
}
