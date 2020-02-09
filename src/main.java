import Simulator.Simulator;

public class main {
    public static void main(String[] args) {
        Simulator sim = new Simulator("simtest",0.1,10,100,7,0.3,24);
        sim.addEvent("PH","BRUIT;2",10);
        sim.Simulation();
    }
}
