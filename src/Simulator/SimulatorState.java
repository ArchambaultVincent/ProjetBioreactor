package Simulator;

/**
 * permet l'enregistrement de l'évolution de la culture
 */
public class SimulatorState {
    private double ph;
    private double Do2;
    private double quantities;
    private double Temp;

    public SimulatorState(double ph, double do2, double quantities, double temp) {
        this.ph = ph;
        Do2 = do2;
        this.quantities = quantities;
        Temp = temp;
    }


    @Override
    public String toString() {
        return ph+";"+Do2+";"+quantities+";"+Temp+";";
    }
}
