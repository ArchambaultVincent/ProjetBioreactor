package DetectionErrorIA;

import java.util.LinkedList;


public class Neuron {
    private double action;
    private double potential;
    private LinkedList<Double> weights;
    private LinkedList<Double> weightsTitle;

    public Neuron() {
        weights = new LinkedList<>();
        weightsTitle = new LinkedList<>();
    }

    public LinkedList<Double> getWeights() {
        return weights;
    }

    public void setWeights(LinkedList<Double> weights) {
        this.weights = weights;
    }

    public double getAction() {
        return action;
    }

    public void setAction(double action) {
        this.action = action;
    }

    public double getPotential() {
        return potential;
    }

    public void setPotential(double potential) {
        this.potential = potential;
    }

    public LinkedList<Double> getWeightsTitle() {
        return weightsTitle;
    }
}
