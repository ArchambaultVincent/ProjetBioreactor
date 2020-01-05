package Simulator;

/**
 * @author Archambault Vincent
 * cette classe permet de simuler les informations réçu par les différents capteur
 *
 */
public class Sensor {
    private double sensor_value;

    private String state;

    public Sensor() {
        state="STABLE";
        sensor_value=0;
    }

    public String getstate() {
        return state;
    }

    public void setstate(String state) {
        state = state;
    }

    public double getSensor_value() {
        return sensor_value;
    }

    public void setSensor_value(double sensor_value) {
        this.sensor_value = sensor_value;
    }





}
