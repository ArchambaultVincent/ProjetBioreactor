package Simulator;

/**
 * @author Archambault Vincent
 * cette classe determine les caractéristique propre a la cellule
 */
public class Cells {
private double saturation=1.34;
private double grothRate ;
private double yield;



    public double getGrothRate() {
        return grothRate;
    }

    public double getSaturation() {
        return saturation;
    }

    public void setSaturation(double saturation) {
        this.saturation = saturation;
    }

    public void setGrothRate(double grothRate) {
        this.grothRate = grothRate;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }
}
