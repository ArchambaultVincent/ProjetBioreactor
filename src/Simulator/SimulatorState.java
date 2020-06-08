package Simulator;

/**
 * permet l'enregistrement de l'évolution de la culture
 */
public class SimulatorState {
    private double ph;
    private double Do2;
    private double quantities;
    private double substrate;
    private double Temp;
    private double debit_dair;
    private double co2;

    public SimulatorState(double ph, double do2, double quantities,double substrate, double temp , double debit_dair , double co2) {
        this.ph = ph;
        this.Do2 = do2;
        this.quantities = quantities;
        this.Temp = temp;
        this.substrate=substrate;
        this.debit_dair=debit_dair;
        this.co2=co2;
    }


    @Override
    public String toString() {
        return ph+";"+Do2+";"+quantities+";"+substrate+";"+Temp+";\n";
    }

    public double getSubstrate() {
        return substrate;
    }

    public void setSubstrate(double substrate) {
        this.substrate = substrate;
    }
}
