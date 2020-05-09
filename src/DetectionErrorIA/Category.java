package DetectionErrorIA;

import java.util.LinkedList;
import static java.lang.Math.abs;

public class Category {
    private LinkedList<Entry> cat;
    private LinkedList<Double> distance;
    private int distanceCat[];

    public Category(int maxSize, int position) {
        cat = new LinkedList<>();
        distance = new LinkedList<>();
        distanceCat = new int[maxSize];

        for (int index = 0; index < maxSize; index++) {
            distanceCat[index] = abs(index - position);
        }
    }

    public LinkedList<Entry> getCategory() {
        return cat;
    }

    public void setRecipes(LinkedList<Entry> cat) {
        this.cat = cat;
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
