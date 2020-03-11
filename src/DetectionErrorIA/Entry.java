package DetectionErrorIA;

import java.util.LinkedList;


public class Entry {
    private LinkedList<PhWeight> phWeights;
    private LinkedList<Do2Weight> do2Weights;
    private LinkedList<TemperatureWeight> temperatureWeights;
    private LinkedList<GrowthWeight> growthWeights;

    // Titles for w/e reasons
    private LinkedList<PhWeight> phWeightsTitles;
    private LinkedList<Do2Weight> do2WeightsTitles;
    private LinkedList<TemperatureWeight> temperatureWeightsTitles;
    private LinkedList<GrowthWeight> growthWeightsTitles;

    private String recipeName;

    public Entry(LinkedList<PhWeight> phWeights, LinkedList<PhWeight> phWeightsTitles,
                 LinkedList<Do2Weight> do2Weights, LinkedList<Do2Weight> do2WeightsTitles,
                 LinkedList<TemperatureWeight> temperatureWeights, LinkedList<TemperatureWeight> temperatureWeightsTitles,
                 LinkedList<GrowthWeight> growthWeights,  LinkedList<GrowthWeight> growthWeightsTitles, String recipeName) {
        this.phWeights = phWeights;
        this.do2Weights = do2Weights;
        this.temperatureWeights = temperatureWeights;
        this.growthWeights = growthWeights;
        this.phWeightsTitles = phWeightsTitles;
        this.do2WeightsTitles = do2WeightsTitles;
        this.temperatureWeightsTitles = temperatureWeightsTitles;
        this.growthWeightsTitles = growthWeightsTitles;
        this.recipeName = recipeName;
    }

    public LinkedList<PhWeight> getPhWeights() {
        return phWeights;
    }

    public void setPhWeights(LinkedList<PhWeight> phWeights) {
        this.phWeights = phWeights;
    }

    public LinkedList<Do2Weight> getDo2Weights() {
        return do2Weights;
    }

    public void setDo2Weights(LinkedList<Do2Weight> do2Weights) {
        this.do2Weights = do2Weights;
    }

    public LinkedList<TemperatureWeight> getTemperatureWeights() {
        return temperatureWeights;
    }

    public void setTemperatureWeights(LinkedList<TemperatureWeight> temperatureWeights) {
        this.temperatureWeights = temperatureWeights;
    }

    public LinkedList<GrowthWeight> getGrowthWeights() {
        return growthWeights;
    }

    public void setGrowthWeights(LinkedList<GrowthWeight> growthWeights) {
        this.growthWeights = growthWeights;
    }

    public LinkedList<PhWeight> getPhWeightsTitles() {
        return phWeightsTitles;
    }

    public void setPhWeightsTitles(LinkedList<PhWeight> phWeightsTitles) {
        this.phWeightsTitles = phWeightsTitles;
    }

    public LinkedList<Do2Weight> getDo2WeightsTitles() {
        return do2WeightsTitles;
    }

    public void setDo2WeightsTitles(LinkedList<Do2Weight> do2WeightsTitles) {
        this.do2WeightsTitles = do2WeightsTitles;
    }

    public LinkedList<TemperatureWeight> getTemperatureWeightsTitles() {
        return temperatureWeightsTitles;
    }

    public void setTemperatureWeightsTitles(LinkedList<TemperatureWeight> temperatureWeightsTitles) {
        this.temperatureWeightsTitles = temperatureWeightsTitles;
    }

    public LinkedList<GrowthWeight> getGrowthWeightsTitles() {
        return growthWeightsTitles;
    }

    public void setGrowthWeightsTitles(LinkedList<GrowthWeight> growthWeightsTitles) {
        this.growthWeightsTitles = growthWeightsTitles;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
