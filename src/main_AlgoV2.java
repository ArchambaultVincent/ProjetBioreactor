import Optimisation.Bio_Parameter;
import Optimisation.Genetic_Algorithm;
import Optimisation.Genetic_Algorithm_V2;
import Optimisation.Q_Learning;
import Simulator.Simulator;

public class main_AlgoV2 {
    public static void main(String[] args) {
        int size=10;
        Genetic_Algorithm_V2 ga = new Genetic_Algorithm_V2();
        Bio_Parameter b = ga.Genetic_Algorithme(0.1,0);
        for (int index = 0; index < size; index++) {
            int time = 100;
            if (index < size-1) {
                 Simulator sim = new Simulator("simtest_" + index, 0.1, 10, time, b.getPh(), b.getDo2(), b.getTemp());
                for (int index2 = 0; index2 <= time; index2++) {
                    sim.SimulationStep();
                    if (index2 % 5 == 0) {
                        b = ga.Genetic_Algorithme(sim.getBiomass(),index2);
                        sim.addEvent("", "PH;" + b.getPh(), index2 + 1);
                        sim.addEvent("", "DO;" + b.getDo2(), index2 + 1);
                        sim.addEvent("", "TEMP;" + b.getTemp(), index2 + 1);
                       // System.out.println(" DO2=" + b.getDo2() + " TEMP=" + b.getTemp() + " PH=" + b.getPh() + " BIOMASS=" + b.getBiomass());
                        }
                    }
                }
                else {
                    b=ga.Get_Best_Param();
                    Simulator sim = new Simulator("simtest_" + index, 0.1, 10, time, b.getPh(), b.getDo2(), b.getTemp());
                    sim.Simulation();
                }
            }
        }
}
