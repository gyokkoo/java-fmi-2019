package bg.sofia.uni.fmi.mjt.authroship.detection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class AuthorshipDetectorImpl implements AuthorshipDetector {
    private static final String SIGNATURES_DATA_SEPARATOR = ",";
    private static final String PUNCTUATION_MATCHER_REGEX = "[!?.]";
    private static final String CLEANUP_REGEX =
            "^[!.,:;\\-?<>#*\'\"\\[(\\])\\n\\t\\\\]+|[!.,:;\\-?<>#*\'\"\\[(\\])\\n\\t\\\\]+$";

    private double[] weights;
    private Map<String, LinguisticSignature> authorSignatures;

    public AuthorshipDetectorImpl(InputStream signaturesDataset, double[] weights) {
        this.weights = weights;
        this.authorSignatures = new HashMap<>();

        parseSignaturesDataset(signaturesDataset);
    }

    @Override
    public LinguisticSignature calculateSignature(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException("Text input stream cannot be null!");
        }

        List<List<String>> sentences = extractSentences(mysteryText);
        if (sentences == null || sentences.size() == 0) {
            throw new IllegalArgumentException("Invalid text stream, cannot find sentences!");
        }

        double wordsCount = 0;
        double totalWordsLength = 0;
        double totalPhasesInText = 0;

        Set<String> uniqueWords = new HashSet<>();
        Set<String> repetitiveWords = new HashSet<>();
        for (List<String> sentence : sentences) {
            int sentencePhaseSymbols = 0;
            for (String word : sentence) {
                if (word.contains(",") || word.contains(":") || word.contains(";")) {
                    sentencePhaseSymbols++;
                }

                String cleanedWord = cleanUp(word);
                if (cleanedWord.isEmpty()) {
                    continue;
                }

                if (!uniqueWords.add(cleanedWord)) {
                    repetitiveWords.add(cleanedWord);
                }

                totalWordsLength += cleanedWord.length();
                wordsCount++;
            }

            if (sentencePhaseSymbols != 0) {
                totalPhasesInText += sentencePhaseSymbols + 1;
            }
        }

        double averageWordLength = totalWordsLength / wordsCount;
        double typeTokenRatio = uniqueWords.size() / wordsCount;
        double hapaxLegomenaRatio = (uniqueWords.size() - repetitiveWords.size()) / wordsCount;
        double averageSentenceLength = wordsCount / sentences.size();
        double averageSentenceComplexity = totalPhasesInText / sentences.size();

        return createLinguisticSignature(averageWordLength, typeTokenRatio, hapaxLegomenaRatio,
                averageSentenceLength, averageSentenceComplexity);
    }

    @Override
    public double calculateSimilarity(LinguisticSignature firstSignature,
                                      LinguisticSignature secondSignature) {
        if (firstSignature == null || secondSignature == null) {
            throw new IllegalArgumentException("Signature value cannot be null!");
        }

        double similaritySum = 0;
        Map<FeatureType, Double> firstFeatures = firstSignature.getFeatures();
        Map<FeatureType, Double> secondFeatures = secondSignature.getFeatures();

        int index = 0;
        for (Map.Entry<FeatureType, Double> features : firstFeatures.entrySet()) {
            double first = features.getValue();
            double second = secondFeatures.get(features.getKey());

            similaritySum += Math.abs(first - second) * weights[index++];
        }

        return similaritySum;
    }

    @Override
    public String findAuthor(InputStream mysteryText) {
        if (mysteryText == null) {
            throw new IllegalArgumentException("Text input stream cannot be null!");
        }

        LinguisticSignature mysterySignature = this.calculateSignature(mysteryText);

        double bestSimilarity = Double.MAX_VALUE;
        String bestMatchingAuthor = null;

        for (Map.Entry<String, LinguisticSignature> entry : authorSignatures.entrySet()) {
            double similarity = calculateSimilarity(mysterySignature, entry.getValue());
            if (similarity < bestSimilarity) {
                bestSimilarity = similarity;
                bestMatchingAuthor = entry.getKey();
            }
        }

        return bestMatchingAuthor;
    }

    private List<List<String>> extractSentences(InputStream mysteryText) {
        List<List<String>> sentences = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(mysteryText))) {
            String line = bfr.readLine();
            List<String> sentence = new ArrayList<>();

            while (line != null) {
                String[] tokens = line.split("\\s+");
                // Extract sentences from given tokens
                for (String word : tokens) {
                    String trimmedWord = word.replaceAll(PUNCTUATION_MATCHER_REGEX, "").trim();

                    if (!trimmedWord.isEmpty()) {
                        sentence.add(trimmedWord);
                    }

                    if (word.endsWith(".") || word.endsWith("?") || word.endsWith("!")) {
                        // Terminate sentence
                        if (sentence.size() != 0) {
                            sentences.add(sentence);
                        }

                        sentence = new ArrayList<>();
                    }
                }

                line = bfr.readLine();
            }

            // Adding last sentence
            sentences.add(sentence);
        } catch (IOException e) {
            System.out.println("Could not extract sentences!");
            e.printStackTrace();
        }

        return sentences;
    }

    private void parseSignaturesDataset(InputStream signaturesDataset) {
        try (BufferedReader bfr = new BufferedReader(new InputStreamReader(signaturesDataset))) {
            String line = bfr.readLine();
            while (line != null) {
                String[] tokens = line.split(SIGNATURES_DATA_SEPARATOR);

                String author = tokens[0];
                double averageWordLength = Double.parseDouble(
                        tokens[FeatureType.AVERAGE_WORD_LENGTH.getSignatureIndex()]);
                double typeTokenRatio = Double.parseDouble(
                        tokens[FeatureType.TYPE_TOKEN_RATIO.getSignatureIndex()]);
                double hapaxLegomenaRatio = Double.parseDouble(
                        tokens[FeatureType.HAPAX_LEGOMENA_RATIO.getSignatureIndex()]);
                double averageSentenceLength = Double.parseDouble(
                        tokens[FeatureType.AVERAGE_SENTENCE_LENGTH.getSignatureIndex()]);
                double averageSentenceComplexity = Double.parseDouble(
                        tokens[FeatureType.AVERAGE_SENTENCE_COMPLEXITY.getSignatureIndex()]);

                LinguisticSignature linguisticSignature = createLinguisticSignature(averageWordLength,
                        typeTokenRatio,
                        hapaxLegomenaRatio,
                        averageSentenceLength,
                        averageSentenceComplexity);

                authorSignatures.put(author, linguisticSignature);

                line = bfr.readLine();
            }
        } catch (IOException e) {
            System.out.println("Could not parse signature dataset!");
            e.printStackTrace();
        }
    }

    private static LinguisticSignature createLinguisticSignature(
            double averageWordLength,
            double typeTokenRatio,
            double hapaxLegomenaRatio,
            double averageSentenceLength,
            double averageSentenceComplexity) {

        Map<FeatureType, Double> features = new EnumMap<>(FeatureType.class);

        features.put(FeatureType.AVERAGE_WORD_LENGTH, averageWordLength);
        features.put(FeatureType.TYPE_TOKEN_RATIO, typeTokenRatio);
        features.put(FeatureType.HAPAX_LEGOMENA_RATIO, hapaxLegomenaRatio);
        features.put(FeatureType.AVERAGE_SENTENCE_LENGTH, averageSentenceLength);
        features.put(FeatureType.AVERAGE_SENTENCE_COMPLEXITY, averageSentenceComplexity);

        return new LinguisticSignature(features);
    }

    private static String cleanUp(String word) {
        return word.toLowerCase().replaceAll(CLEANUP_REGEX, "");
    }
}