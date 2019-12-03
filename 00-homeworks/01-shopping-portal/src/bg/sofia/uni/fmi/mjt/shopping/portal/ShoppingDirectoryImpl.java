package bg.sofia.uni.fmi.mjt.shopping.portal;

import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.NoOfferFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.OfferAlreadySubmittedException;
import bg.sofia.uni.fmi.mjt.shopping.portal.exceptions.ProductNotFoundException;
import bg.sofia.uni.fmi.mjt.shopping.portal.offer.Offer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ShoppingDirectoryImpl implements ShoppingDirectory {

    // Including the current day
    private static final int DAYS_IN_RANGE = 29;

    private Map<String, Set<Offer>> offersByProductName = new HashMap<>();

    @Override
    public Collection<Offer> findAllOffers(String productName) throws ProductNotFoundException {
        if (productName == null) {
            throw new IllegalArgumentException("Product name cannot be null!");
        }

        Set<Offer> productOffers = offersByProductName.get(productName);
        if (productOffers == null) {
            throw new ProductNotFoundException("There is no product with name" + productName);
        }

        List<Offer> resultOffers = new ArrayList<>(productOffers);

        resultOffers.removeIf(offer -> offer.getDate()
                .isBefore(LocalDate.now().minusDays(DAYS_IN_RANGE)));

        resultOffers.sort(Comparator.comparingDouble(Offer::getTotalPrice));

        return resultOffers;
    }

    @Override
    public Offer findBestOffer(String productName)
            throws ProductNotFoundException, NoOfferFoundException {

        Collection<Offer> validOffers = findAllOffers(productName);

        if (validOffers == null || validOffers.size() == 0) {
            throw new NoOfferFoundException(
                    String.format("Offer for product %s does not exists", productName));
        }

        return validOffers.iterator().next();
    }

    @Override
    public Collection<PriceStatistic> collectProductStatistics(String productName)
            throws ProductNotFoundException {

        if (productName == null) {
            throw new IllegalArgumentException("Product name cannot be null!");
        }

        if (!offersByProductName.containsKey(productName)) {
            throw new ProductNotFoundException("There is no product with name" + productName);
        }

        Set<Offer> productOffers = this.offersByProductName.get(productName);

        Map<LocalDateTime, List<Offer>> offersByDay = new HashMap<>();
        for (Offer offer : productOffers) {
            List<Offer> productDayOffers = offersByDay.get(offer.getDate().atStartOfDay());
            if (productDayOffers == null) {
                productDayOffers = new ArrayList<>();
            }

            productDayOffers.add(offer);
            offersByDay.put(offer.getDate().atStartOfDay(), productDayOffers);
        }

        List<PriceStatistic> statistics = new ArrayList<>();
        for (Map.Entry<LocalDateTime, List<Offer>> offerByDay : offersByDay.entrySet()) {
            statistics.add(new PriceStatistic(offerByDay.getKey().toLocalDate(), offerByDay.getValue()));
        }

        statistics.sort(Comparator.comparing(PriceStatistic::getDate).reversed());

        return statistics;
    }

    @Override
    public void submitOffer(Offer offer) throws OfferAlreadySubmittedException {
        if (offer == null) {
            throw new IllegalArgumentException("Offer cannot be null!");
        }

        Set<Offer> productOffers = offersByProductName.get(offer.getProductName());
        if (productOffers == null) {
            productOffers = new HashSet<>();
        }

        // Contains on set collection is O(1)
        if (productOffers.contains(offer)) {
            throw new OfferAlreadySubmittedException(String.format(
                    "Offer for product %s already exists!", offer.getProductName()));
        }

        if (productOffers.size() == 0) {
            this.offersByProductName.put(offer.getProductName(), new HashSet<>());
        }

        productOffers.add(offer);
        this.offersByProductName.put(offer.getProductName(), productOffers);
    }
}
