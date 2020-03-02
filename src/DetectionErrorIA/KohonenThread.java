package DetectionErrorIA;

import com.ucp.cookwithease.model.Recipe;

import java.util.LinkedList;


public class KohonenThread extends Thread {
    private LinkedList<String> titles;
    private LinkedList<String> ingredients;
    private LinkedList<Recipe> recipes;
    private Kohonen kohonen;
    private String type;

    public KohonenThread(LinkedList<String> ingredients, LinkedList<Recipe> recipes, LinkedList<String> titles, String type) {
        this.titles = titles;
        this.recipes = recipes;
        this.ingredients = ingredients;
        this.type = type;
    }

    public void run() {
        System.out.println("[KOHONEN: " + type.toUpperCase() + "] Initialization...");
        kohonen = new Kohonen(ingredients, recipes, titles, type);

        System.out.println("[KOHONEN: " + type.toUpperCase() + "] Clustering...");
        kohonen.clusterize();

        System.out.println("[KOHONEN: " + type.toUpperCase() + "] Finished!");
    }

    public Kohonen getKohonen() {
        return kohonen;
    }
}
