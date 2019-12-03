package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class OfferAlreadySubmittedException extends Exception {

    private static final String DEFAULT_MESSAGE = "Offer is already submitted!";

    public OfferAlreadySubmittedException() {
        this(DEFAULT_MESSAGE);
    }

    public OfferAlreadySubmittedException(String errorMessage) {
        super(errorMessage);
    }
}
