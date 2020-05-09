package DetectionErrorIA;

import java.util.LinkedList;

public class KohonenThread extends Thread {
    private LinkedList<Error> errors;
    private LinkedList<String> settings;
    private Kohonen kohonen;
    private String type;

    public KohonenThread(LinkedList<String> settings, String type) {
        this.settings = settings;
        this.type = type;
    }

    public void run() {
        System.out.println("[KOHONEN: " + type.toUpperCase() + "] Initialization...");
        kohonen = new Kohonen(settings, type);

        System.out.println("[KOHONEN: " + type.toUpperCase() + "] Clustering...");
        kohonen.clusterize();

        System.out.println("[KOHONEN: " + type.toUpperCase() + "] Finished!");
    }

    public Kohonen getKohonen() {
        return kohonen;
    }
}
