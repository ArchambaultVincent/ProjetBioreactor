import Optimisation.Bio_Parameter;
import Optimisation.Genetic_Algorithm;
import Optimisation.Q_Learning;
import Simulator.Simulator;

public class main_Qlearning {
    public static void main(String[] args) {
        Genetic_Algorithm ga = new Genetic_Algorithm();
        Bio_Parameter b = ga.Randomizer();
        Q_Learning q_learning= new Q_Learning((int)b.getPh(),(int)b.getDo2(),(int)b.getTemp());
        for (int index = 0; index < 10; index++) {
            int time=100;
            Simulator sim = new Simulator("simtest_"+index, 0.1, 10, time, b.getPh(), b.getDo2(), b.getTemp());
            if(index < 9) {
                for (int index2 = 0; index2 < time + 1; index2++) {
                    sim.SimulationStep();
                    if (index2 % 5 == 0) {
                        int action = q_learning.Action();
                        q_learning.reforcement(sim.getBiomass());
                        b = q_learning.Get_Parameter();
                        switch (action) {
                            case 0:
                                sim.addEvent("", "PH;" + b.getPh(), index2 + 1);
                                break;
                            case 1:
                                sim.addEvent("", "PH;" + b.getPh(), index2 + 1);
                                break;
                            case 2:
                                sim.addEvent("", "DO;" + b.getDo2(), index2 + 1);
                                break;
                            case 3:
                                sim.addEvent("", "DO;" + b.getDo2(), index2 + 1);
                                break;
                            case 4:
                                sim.addEvent("", "TEMP;" + b.getTemp(), index2 + 1);
                                break;
                            case 5:
                                sim.addEvent("", "TEMP;" + b.getTemp(), index2 + 1);
                                break;
                        }
                    }
                }
            }
            else{
                sim.Simulation();
            }
        }
    }
}
