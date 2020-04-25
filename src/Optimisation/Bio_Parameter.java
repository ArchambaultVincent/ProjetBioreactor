package Optimisation;

public class Bio_Parameter {
   private double Ph;
   private double Temp;
   private double Do2;
   private double Biomass;

    public Bio_Parameter( double ph, double temp, double do2, double Biomass) {
        this.Ph = ph;
        this.Temp = temp;
        this.Do2 = do2;
        this.Biomass=Biomass;
    }


    public double getPh() {
        return Ph;
    }

    public void setPh(double ph) {
        Ph = ph;
    }

    public double getTemp() {
        return Temp;
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getDo2() {
        return Do2;
    }

    public void setDo2(double do2) {
        Do2 = do2;
    }

    public double getBiomass() {
        return Biomass;
    }

    public void setBiomass(double biomass) {
        Biomass = biomass;
    }
}
