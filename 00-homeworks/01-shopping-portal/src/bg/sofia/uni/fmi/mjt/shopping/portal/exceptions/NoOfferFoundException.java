package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class NoOfferFoundException extends Exception {

    private static final String DEFAULT_MESSAGE = "There is no such offer!";

    public NoOfferFoundException() {
        this(DEFAULT_MESSAGE);
    }

    public NoOfferFoundException(String errorMessage) {
        super(errorMessage);
    }
}
