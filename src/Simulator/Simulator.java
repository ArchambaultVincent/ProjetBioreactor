package Simulator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    double tempPh[][] = new double[40][10];
    public Simulator(String simulationName, double quantities,double substrate_concentration, int simulationTime,double Ph,double Do2, double Temp) {
        this.biomass = quantities;
        this.simulationTime=simulationTime;
        this.simulationName = simulationName;
        this.substrate_concentration=substrate_concentration;
        ph_Sensor = new Sensor();
        do2_Sensor = new Sensor();
        temp_Sensor = new Sensor();
        sim = new ArrayList<SimulatorState>();
        events = new ArrayList<Event>();
        cells=new Cells();
        this.Ph=Ph;
        this.Do2=Do2;
        this.Temp=Temp;
        grothRate_Init();
    }

    public void grothRate_Init(){
        StringBuilder sb = new StringBuilder();
        int index=0;
        try (FileReader reader = new FileReader("./Simulation/TempPh.csv");
             BufferedReader br = new BufferedReader(reader)) {
            // read line by line
            String line;
            while ((line = br.readLine()) != null) {
                String linesplit[]  = line.split(";");
                for(int index2=0;index2 < linesplit.length;index2++){
                    tempPh[index][index2]=Double.parseDouble(linesplit[index2]);
                }
                index++;
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
    /**
     * calcul la croissance specifique maximal
     */
    private void grothRate_Calculation(){
        double grothrateMax=tempPh[(int) Math.round(Temp)][(int)Math.round(Ph)];
        double Yell=0;
        if(Do2  < 0.1){
            grothrateMax+=growth_rate*0.05;
            Yell=0.16;
        }
        else if(Do2  < 0.2){
            grothrateMax+=growth_rate*0.048;
            Yell=0.19;
        }
        else if(Do2  < 0.4){
            grothrateMax+=growth_rate*0.06;
            Yell=0.16;
        }
        else if(Do2  < 0.5){
            grothrateMax+=growth_rate*0.03;
            Yell=0.16;
        }
        else if(Do2  < 0.7){
            grothrateMax+=growth_rate*0.04;
            Yell=0.16;
        }
        else{
            grothrateMax+=growth_rate*0.02;
            Yell=0.18;
        }
        cells.setGrothRate(grothrateMax);
        cells.setYield(Yell);
    }

    /**
     *
     */
    public void Production(){
        grothRate_Calculation();
        growth_rate=(cells.getGrothRate()*substrate_concentration)/(cells.getSaturation()+substrate_concentration);
        biomass_rate=growth_rate*biomass;
        substrate_rate=-biomass_rate/cells.getYield();
        //product_rate=(k1+k2*cells.getGrothRate())*biomass;
        biomass=biomass+biomass_rate*biomass;
        substrate_concentration=substrate_concentration+substrate_rate;
        if(substrate_concentration < 0)
            substrate_concentration=0;
        //product=product_rate*time;
    }

    /**
     *
     */
    public void Simulation(){
        for( time=0;time < simulationTime ; time++){
            if(!events.isEmpty()) {
                while(!events.isEmpty() && events.get(0).getTimestamp() == time) {
                        AnalyseEvent(events.get(0));
                        events.remove(0);
                }
            }
            Production();
            ph_Sensor.setSensor_value(Ph);
            temp_Sensor.setSensor_value(Temp);
            do2_Sensor.setSensor_value(Do2);
            SimulatorState state=new SimulatorState(ph_Sensor.getSensor_value(),do2_Sensor.getSensor_value(),biomass,substrate_concentration,temp_Sensor.getSensor_value());
            sim.add(state);
        }
        writeResult();
    }

    /**
     * fonction permet
     */
    public void SimulationStep(){
        if(time < simulationTime){
            if(!events.isEmpty()) {
                while(!events.isEmpty() && events.get(0).getTimestamp() == time) {
                    AnalyseEvent(events.get(0));
                    events.remove(0);
                }
            }
            Production();
            ph_Sensor.setSensor_value(Ph);
            temp_Sensor.setSensor_value(Temp);
            do2_Sensor.setSensor_value(Do2);
            SimulatorState state=new SimulatorState(ph_Sensor.getSensor_value(),do2_Sensor.getSensor_value(),biomass,substrate_concentration,temp_Sensor.getSensor_value());
            sim.add(state);
            time++;
        }
        else{
            //writeResult();
        }
    }

    /**
     *
     * @param target
     * @param type
     * @param timestamp
     */
    public void addEvent(String target, String type , int timestamp){
        Sensor sensor=null;
        switch (target){
            case "PH":
                sensor =ph_Sensor;
                break;
            case "TEMP":
                sensor = temp_Sensor;
                break;
            case "DO" :
                sensor=do2_Sensor;
                break;
        }
        Event e = new Event(sensor,type,timestamp);
        events.add(e);
    }

    public void AnalyseEvent(Event e){
        String[] Event=e.getType().split(";");
        switch(Event[0]){
            case "PH":
                Ph=Double.parseDouble(Event[1]);
                break;
            case "TEMP":
                Temp=Double.parseDouble(Event[1]);
                break;
            case "DO":
                Do2=Double.parseDouble(Event[1]);
                break;
            case "BRUIT":
                e.getTarget().setstate("BRUIT");
                e.getTarget().setBruit(Integer.parseInt(Event[1]));
                break;
            case "OFF":
                e.getTarget().setstate("OFF");
                break;
            case "ON":
                e.getTarget().setstate("ON");
                break;
        };
    }
    public void writeResult(){
        BufferedWriter writer = null;
        String data=" ";
        try {
            writer = new BufferedWriter(new FileWriter("./Simulation/result/"+simulationName+".csv"));
            for(SimulatorState state : sim){
                data=data+state.toString();
            }
            writer.write(data);
            writer.close();
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
        return temp_Sensor.getSensor_value();
    }

    public void setTemp(double temp) {
        Temp = temp;
    }

    public double getDo2() {
        return do2_Sensor.getSensor_value();
    }
    public double getBiomass() {
        return biomass;
    }

    public void setDo2(double do2) {
        Do2 = do2;
    }

    public double getPh() {
        return ph_Sensor.getSensor_value();
    }

    public void setPh(double ph) {
        Ph = ph;
    }


    public double getSubstrat() {  return substrate_concentration;    }
}
