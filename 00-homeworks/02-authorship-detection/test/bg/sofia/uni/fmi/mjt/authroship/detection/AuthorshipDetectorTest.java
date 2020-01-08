package bg.sofia.uni.fmi.mjt.authroship.detection;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class AuthorshipDetectorTest {
    private static final String KNOWN_SIGNATURES_PATH = "src/resources/knownSignatures.txt";
    private static final String SAMPLE_TEXT_PATH = "src/resources/mysteryFiles/sample.txt";
    private static final String LEWIS_CARROLL_TEXT_PATH = "src/resources/mysteryFiles/mystery2.txt";
    private static final double[] TESTING_WEIGHTS = {11, 33, 50, 0.4, 4};

    private AuthorshipDetector authorshipDetector;

    @Before
    public void setUp() throws IOException {
        try (InputStream signatureInputStream = new FileInputStream(KNOWN_SIGNATURES_PATH)) {
            authorshipDetector = new AuthorshipDetectorImpl(signatureInputStream, TESTING_WEIGHTS);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSignatureWithNullText() {
        authorshipDetector.calculateSignature(null);
    }

    @Test()
    public void testCalculateSignature() throws IOException {
        try (InputStream sampleTextStream = new FileInputStream(SAMPLE_TEXT_PATH)) {
            LinguisticSignature sampleSignature =
                    authorshipDetector.calculateSignature(sampleTextStream);

            assertNotNull(sampleSignature.getFeatures().get(FeatureType.AVERAGE_WORD_LENGTH));
            assertNotNull(sampleSignature.getFeatures().get(FeatureType.TYPE_TOKEN_RATIO));
            assertNotNull(sampleSignature.getFeatures().get(FeatureType.HAPAX_LEGOMENA_RATIO));
            assertNotNull(sampleSignature.getFeatures().get(FeatureType.AVERAGE_SENTENCE_LENGTH));
            assertNotNull(sampleSignature.getFeatures().get(FeatureType.AVERAGE_SENTENCE_COMPLEXITY));
            assertEquals(FeatureType.values().length, sampleSignature.getFeatures().size());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAuthorWithNullText() {
        authorshipDetector.findAuthor(null);
    }

    @Test()
    public void testFindAuthorShouldRecognizeMysteryText() throws IOException {
        try (InputStream mysteryTextStream = new FileInputStream(LEWIS_CARROLL_TEXT_PATH)) {
            String detectedAuthor = authorshipDetector.findAuthor(mysteryTextStream);
            String expectedAuthor = "Lewis Carroll";

            assertEquals(expectedAuthor, detectedAuthor);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateSimilarityWithNull() {
        authorshipDetector.calculateSimilarity(null, null);
    }

    @Test()
    public void testCalculateSimilarity() throws IOException {
        try (InputStream sampleTextStream = new FileInputStream(SAMPLE_TEXT_PATH)) {
            LinguisticSignature sampleSignature =
                    authorshipDetector.calculateSignature(sampleTextStream);

            double similarity = authorshipDetector
                    .calculateSimilarity(sampleSignature, sampleSignature);
            assertEquals(0, similarity, 0.0);
        }
    }
}