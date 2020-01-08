package bg.sofia.uni.fmi.mjt.authroship.detection;

import java.util.Collections;
import java.util.Map;

public class LinguisticSignature {

    private Map<FeatureType, Double> features;

    public LinguisticSignature(Map<FeatureType, Double> features) {
        this.features = features;
    }

    public Map<FeatureType, Double> getFeatures() {
        return Collections.unmodifiableMap(features);
    }
}