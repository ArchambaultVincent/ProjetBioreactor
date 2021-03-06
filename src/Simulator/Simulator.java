package Simulator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Simulator {
    private float Reactor_volume;
    private double biomass;


    private double substrate_concentration;
    private double product;
    private double growth_rate;
    private double biomass_rate;
    private double substrate_rate;
    private float product_rate;
    private float k1;
    private float k2;
    private Cells cells;
    private int simulationTime;
    private float Ph;
    private float Do2;
    private float Temp;
    private float Co2;
    private float debit_dair;
    private float vitesse_rotation=0;



    private int time=0;
    // on modifie les paramètres Senseur et réaction pour les cas d'erreur
    private Sensor ph_Sensor;
    private Sensor do2_Sensor;
    private Sensor temp_Sensor;
    private String simulationName;
    private ArrayList<SimulatorState> sim;
    private ArrayList<Event> events;
    double tempPh[][] = new double[40][10];
    public Simulator(String simulationName, float quantities,float substrate_concentration, int simulationTime,float Ph,float Do2, float Temp,float debit_dair) {
        this.biomass = quantities;
        this.simulationTime=simulationTime;
        this.simulationName = simulationName;
        this.substrate_concentration=substrate_concentration;
        this.debit_dair=debit_dair;
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
        double phget=Ph;
        float tempget=Temp;
        if(tempget>39){
            tempget=39;
        }
        if(tempget < 0){
            tempget = 0.0f;
        }
        if(phget > 9){
            phget = 9;
        }
        if(phget < 0){
            phget = 0;
        }
        double grothrateMax=tempPh[(int) Math.round(tempget)][(int)Math.round(phget)];
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
            SimulatorState state=new SimulatorState(ph_Sensor.getSensor_value(),do2_Sensor.getSensor_value(),biomass,substrate_concentration,temp_Sensor.getSensor_value(),debit_dair,Co2);
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
            SimulatorState state=new SimulatorState(ph_Sensor.getSensor_value(),do2_Sensor.getSensor_value(),biomass,substrate_concentration,temp_Sensor.getSensor_value(),debit_dair,Co2);
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
                Ph=Float.parseFloat(Event[1]);
                break;
            case "TEMP":
                Temp=Float.parseFloat(Event[1]);
                break;
            case "DO":
                Do2=Float.parseFloat(Event[1]);
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

    public float getReactor_volume() {
        return Reactor_volume;
    }

    public void setReactor_volume(float reactor_volume) {
        Reactor_volume = reactor_volume;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(int simulationTime) {
        this.simulationTime = simulationTime;
    }

    public float getTemp() {
        return temp_Sensor.getSensor_value();
    }

    public void setTemp(float temp) {
        Temp = temp;
    }

    public float getDo2() {
        return do2_Sensor.getSensor_value();
    }

    public double getBiomass() {
        return biomass;
    }

    public void setDo2(float do2) {
        Do2 = do2;
    }

    public float getPh() {
        return ph_Sensor.getSensor_value();}

    public void setPh(float ph) {
        Ph = ph;
    }

    public double getSubstrat() {  return substrate_concentration;}

    public float getCo2() {
        return Co2;
    }

    public void setCo2(float co2) {
        Co2 = co2;
    }

    public float getDebit_dair() {
        return debit_dair;
    }

    public void setDebit_dair(float debit_dair) {
        this.debit_dair = debit_dair;
    }

    public void calcul_CO2(){
        Co2= (float) (biomass_rate/debit_dair);
    }

    public float getVitesse_rotation() {
        return vitesse_rotation;
    }

    public void setVitesse_rotation(float vitesse_rotation) {
        this.vitesse_rotation = vitesse_rotation;
    }



}
