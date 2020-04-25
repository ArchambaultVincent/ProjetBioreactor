import Optimisation.Bio_Parameter;
import Optimisation.Genetic_Algorithm;
import Simulator.Simulator;

public class main_AlgoGenetic {
    public static void main(String[] args) {

        for (int index = 0; index < 1000; index++) {
            Bio_Parameter b = null;
            Genetic_Algorithm ga = new Genetic_Algorithm();
            if (index < 2) {
                b = ga.Randomizer();
            } else {
                b = ga.Genetic_Algorithme("./Simulation/result/");
            }
            Simulator sim = new Simulator("simtest_"+index, 0.1, 10, 100, b.getPh(), b.getDo2(), b.getTemp());
            sim.Simulation();
            System.out.println(" DO2="+b.getDo2()+" TEMP="+b.getTemp()+" PH="+b.getPh()+" BIOMASS="+b.getBiomass());
        }
    }
}
