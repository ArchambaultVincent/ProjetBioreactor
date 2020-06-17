package Simulator;

/**
 * @author Archambault Vincent
 * cette classe permet de simuler les informations réçu par les différents capteur
 *
 */
public class Sensor {
    private float sensor_value;


    private float bruit;
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

    public float getSensor_value() {
        if(state.equals("OFF")) {
            return 0;
        }
        else {
            return sensor_value;
        }
    }

    public void setSensor_value(float sensor_value) {
        this.sensor_value = sensor_value;
    }


    public float getBruit() {
        return bruit;
    }

    public void setBruit(float bruit) {
        this.bruit = bruit;
    }




}
