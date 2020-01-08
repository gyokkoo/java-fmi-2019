package bg.sofia.uni.fmi.mjt.authroship.detection;

public enum FeatureType {

    AVERAGE_WORD_LENGTH(1),
    TYPE_TOKEN_RATIO(2),
    HAPAX_LEGOMENA_RATIO(3),
    AVERAGE_SENTENCE_LENGTH(4),
    AVERAGE_SENTENCE_COMPLEXITY(5);

    private int signatureIndex;

    FeatureType(int signatureIndex) {
        this.signatureIndex = signatureIndex;
    }

    public int getSignatureIndex() {
        return signatureIndex;
    }
}