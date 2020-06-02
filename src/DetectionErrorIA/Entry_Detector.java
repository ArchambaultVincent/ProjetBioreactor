package DetectionErrorIA;

import java.util.ArrayList;

public class Entry_Detector {
    private ArrayList<ArrayList<Double>> listDatatrain  = new ArrayList<>();

    public Entry_Detector(ArrayList<ArrayList<Double>> listDatatrain) {
        this.listDatatrain = listDatatrain;
    }

    public ArrayList<ArrayList<Double>> getListDatatrain() {
        return listDatatrain;
    }


    public double Get_LastValue(int index){
        return listDatatrain.get(index).get(listDatatrain.get(index).size()-1);
    }
    public void setListDatatrain(ArrayList<ArrayList<Double>> listDatatrain) {
        this.listDatatrain = listDatatrain;
    }
}
