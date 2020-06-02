package DetectionErrorIA;

import java.util.LinkedList;

public class KohonenThread extends Thread {
    private LinkedList<Error> errors;
    private String settings;
    private Kohonen kohonen;
    private String type;

    public KohonenThread(String setting) {
        this.settings = setting;
    }

    public void run() {
        System.out.println("[KOHONEN: " +"] Initialization...");
        kohonen = new Kohonen(settings);

        System.out.println("[KOHONEN: " + "] Clustering...");
        kohonen.clusterize();
        kohonen.reshapecluster();
        System.out.println("[KOHONEN: " + "] Finished!");
    }

    public Kohonen getKohonen() {
        return kohonen;
    }
}
