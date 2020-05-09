package DetectionErrorIA;

import java.util.LinkedList;


public class Entry {
    private LinkedList<Double> data;

    private String errorName;

    public Entry(LinkedList<Double> data,  String errorName) {
        this.data = data;
        this.errorName = errorName;
    }

    public LinkedList<Double> getData() {
        return data;
    }

    public void setData(LinkedList<Double> data) {
        this.data = data;
    }


    public String getName() {
        return errorName;
    }
}
