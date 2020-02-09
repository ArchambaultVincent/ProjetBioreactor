package Simulator;

/**
 * @author Archambault Vincent
 * cette classe permet de simuler les informations réçu par les différents capteur
 *
 */
public class Sensor {
    private double sensor_value;


    private double bruit;
    private String state;

    public Sensor() {
        state="STABLE";
        sensor_value=0;
        bruit=0;
    }

    public String getstate() {
        return state;
    }

    public void setstate(String state) {
        this.state = state;
    }

    public double getSensor_value() {
        if(state.equals("OFF")) {
            return 0;
        }
        else {
            return sensor_value-bruit+(Math.random()*bruit*2);
        }
    }

    public void setSensor_value(double sensor_value) {
        this.sensor_value = sensor_value;
    }


    public double getBruit() {
        return bruit;
    }

    public void setBruit(double bruit) {
        this.bruit = bruit;
    }




}
