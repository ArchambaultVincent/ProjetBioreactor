package DetectionErrorIA;

import java.util.LinkedList;


public class Entry {
    private LinkedList<PhWeight> data;
    private LinkedList<PhWeight> dataTitles;
    private String recipeName;

    public Entry(LinkedList<PhWeight> data, LinkedList<PhWeight> dataTitles, String recipeName) {
        this.data = data;
        this.dataTitles = dataTitles;
        this.recipeName = recipeName;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public LinkedList<PhWeight> getData() {
        return data;
    }

    public void setData(LinkedList<PhWeight> data) {
        this.data = data;
    }

    public LinkedList<PhWeight> getDataTitles() {
        return dataTitles;
    }

    public void setDataTitles(LinkedList<PhWeight> dataTitles) {
        this.dataTitles = dataTitles;
    }
}
