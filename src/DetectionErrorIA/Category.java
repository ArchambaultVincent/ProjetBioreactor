package DetectionErrorIA;

import java.util.LinkedList;
import static java.lang.Math.abs;

public class Category {
    private LinkedList<Error> errors;
    private LinkedList<Double> distance;
    private int distanceCat[];

    public Category(int maxSize, int position) {
        errors = new LinkedList<>();
        distance = new LinkedList<>();
        distanceCat = new int[maxSize];

        for (int index = 0; index < maxSize; index++) {
            distanceCat[index] = abs(index - position);
        }
    }

    public LinkedList<Error> getRecipes() {
        return errors;
    }

    public void setRecipes(LinkedList<Error> errors) {
        this.errors = errors;
    }

    public int getDistanceCat(int index) {
        return distanceCat[index];
    }

    public LinkedList<Double> getDistance() {
        return distance;
    }

    public void setDistance(LinkedList<Double> distance) {
        this.distance = distance;
    }
}
