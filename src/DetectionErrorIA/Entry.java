package DetectionErrorIA;

import java.util.LinkedList;


public class Entry {
    private LinkedList<SettingsWeight> data;

    // Titles for w/e reasons
    private LinkedList<SettingsWeight> dataTitles;

    private String errorName;

    public Entry(LinkedList<SettingsWeight> data, LinkedList<SettingsWeight> dataTitles, String errorName) {
        this.data = data;
        this.dataTitles = dataTitles;
        this.errorName = errorName;
    }

    public LinkedList<SettingsWeight> getData() {
        return data;
    }

    public void setData(LinkedList<SettingsWeight> data) {
        this.data = data;
    }

    public LinkedList<SettingsWeight> getDataTitles() {
        return dataTitles;
    }

    public void setDataTitles(LinkedList<SettingsWeight> dataTitles) {
        this.dataTitles = dataTitles;
    }

    public String getErrorName() {
        return errorName;
    }

    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }
}
