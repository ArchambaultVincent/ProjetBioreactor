package Simulator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Simulator {
    private double Reactor_volume;
    private double biomass;
    private double substrate_concentration;
    private double product;
    private double growth_rate;
    private double biomass_rate;
    private double substrate_rate;
    private double product_rate;
    private double k1;
    private double k2;
    private Cells cells;
    private int simulationTime;
    private double Ph;
    private double Do2;
    private double Temp;
    private int time=0;
    // on modifie les paramètres Senseur et réaction pour les cas d'erreur
    private Sensor ph_Sensor;
    private Sensor do2_Sensor;
    private Sensor temp_Sensor;
    private String simulationName;
    private ArrayList<SimulatorState> sim;
    private ArrayList<Event> events;
    public Simulator(String simulationName, double quantities, int simulationTime) {
        this.biomass = quantities;
        this.simulationTime=simulationTime;
        this.simulationName = simulationName;
        ph_Sensor = new Sensor();
        do2_Sensor = new Sensor();
        cells=new Cells();
    }

    /**
     * calcul la croissance specifique
     */
    void grothRate_Calculation(){
        
        cells.setGrothRate(0);
    }

    /**
     *
     */
    void Production(){
        grothRate_Calculation();
        growth_rate=(cells.getGrothRate()*substrate_rate)/(cells.getSaturation()+substrate_rate);
        biomass_rate=biomass*cells.getGrothRate();
        substrate_rate=-biomass_rate/cells.getYield();
        product_rate=(k1+k2*cells.getGrothRate())*biomass;
        biomass=biomass_rate*time;
        substrate_concentration=substrate_rate*time;
        product=product_rate*time;
    }

    /**
     *
     */
    void Simulator(){
        for( time=0;time < simulationTime ; time++){
            Production();
            SimulatorState state=new SimulatorState(ph_Sensor.getSensor_value(),do2_Sensor.getSensor_value(),biomass,temp_Sensor.getSensor_value());
            sim.add(state);
        }
        writeResult();
    }

    /**
     * fonction permet
     */
    void SimulationStep(){
        if(time < simulationTime){
            Production();
            SimulatorState state=new SimulatorState(ph_Sensor.getSensor_value(),do2_Sensor.getSensor_value(),biomass,temp_Sensor.getSensor_value());
            sim.add(state);
            time++;
        }
        else{
            writeResult();
        }
    }

    /**
     *
     * @param target
     * @param type
     * @param timestamp
     */
    public void addEvent(Sensor target, String type , int timestamp){
        Event e = new Event(target,type,timestamp);
        events.add(e);
    }

    void writeResult(){
        BufferedWriter writer = null;
        String data=" ";
        try {
            writer = new BufferedWriter(new FileWriter("./src/main/resources/"+simulationName+".csv"));
            PrintWriter print = new PrintWriter(writer);
            for(SimulatorState state : sim){
                data=data+state.toString();
            }
            print.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Cells getCells() {
        return cells;
    }

    public void setCells(Cells cells) {
        this.cells = cells;
    }

    public double getReactor_volume() {
        return Reactor_volume;
    }

    public void setReactor_volume(double reactor_volume) {
        Reactor_volume = reactor_volume;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
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

    public double getPh() {
        return Ph;
    }

    public void setPh(double ph) {
        Ph = ph;
    }
}
