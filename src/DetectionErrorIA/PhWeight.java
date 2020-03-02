package DetectionErrorIA;


public class PhWeight {
    private Double weight;
    private String name;

    public PhWeight(String name) {
        weight = 0.0;
        this.name = name;
    }

    public Double getWeight() {
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
