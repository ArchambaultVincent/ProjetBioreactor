package Simulator;

public class Event {
    private int timestamp;
    private String type;
    private Sensor target;

    public Event(Sensor target, String type, int timestamp) {
        this.timestamp=timestamp;
        this.type=type;
        this.target=target;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Sensor getTarget() {
        return target;
    }

    public void setTarget(Sensor target) {
        this.target = target;
    }
}
