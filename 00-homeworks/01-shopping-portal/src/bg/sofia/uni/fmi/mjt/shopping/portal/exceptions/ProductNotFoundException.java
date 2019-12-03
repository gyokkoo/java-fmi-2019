package bg.sofia.uni.fmi.mjt.shopping.portal.exceptions;

public class ProductNotFoundException extends Exception {

    private static final String DEFAULT_MESSAGE = "There is no such product!";

    public ProductNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ProductNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
