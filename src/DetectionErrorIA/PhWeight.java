package DetectionErrorIA;


public class PhWeight {
    private double weight;
    private String name;

    public PhWeight(String name) {
        weight = 0.0;
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
