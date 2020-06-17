package Optimisation;

public class Bio_Parameter {
   private double Ph;
   private double Temp;
   private double Do2;

    public double getSubstract() {
        return Substract;
    }

    public void setSubstract(double substract) {
        Substract = substract;
    }

    private double Biomass;
   private double Substract;
    private double Co=0;
    private double debit=0;


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

    @Override
    public String toString() {
        return   Ph +";" + Temp + ";" + Do2 +";" + Co + ";" + debit;
    }

    public double getCo() {
        return Co;
    }

    public void setCo(double co) {
        Co = co;
    }

    public double getDebit() {
        return debit;
    }

    public void setDebit(double debit) {
        this.debit = debit;
    }
}
