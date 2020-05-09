package DetectionErrorIA;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import static java.lang.Math.*;


public class Kohonen {
    private static final int DVP = 7;
    private static final int DVN = 9;
    private static final double EPSILON = 0.5;
    private static final double ALPHA = 0.125;
    private static final double BETA = 0.125;
    private static final int NUM_NEURONS = 10;
    private static final int NUM_LEARN = 100;

    private String type;
    private LinkedList<Neuron> neurons;
    private LinkedList<Entry> entries;
    private LinkedList<Category> clusters;
    private LinkedList<String> titles;
    private LinkedList<Integer> pickedEntries;

    public Kohonen(LinkedList<String> settings,String type) {
        this.type = type;

        this.clusters = new LinkedList<>();
        this.neurons = new LinkedList<>();
        this.entries = new LinkedList<>();
        this.pickedEntries = new LinkedList<>();


        initWeights(settings);
    }

    private int pickEntry() {
        if (pickedEntries.size() == 0) {
            for (int i = 0; i < entries.size(); i++) {
                pickedEntries.addLast(i);
            }
        }

        int pickedIndex = (int) (Math.random() * (pickedEntries.size() - 1));
        int result = pickedEntries.get(pickedIndex);
        pickedEntries.remove(pickedIndex);

        return result;
    }

    public void initWeights(LinkedList<String> settings) {
        for (int index = 0; index < NUM_NEURONS; index++) {
            Neuron neuron = new Neuron();
            Category category = new Category(NUM_NEURONS, index);

            for (String ing : settings) {
                double val = random();
                neuron.getWeights().add(val);
            }

            for (String title : titles) {
                neuron.getWeightsTitle().add(0.0);
            }

            neurons.add(neuron);
            clusters.add(category);
        }
    }

    double neighbourhoodFunction(int value) {
        if (value <= DVP) {
            return 1 - (ALPHA * value);

        } else if (value > DVP && value <= DVN) {
            return -BETA * (value - DVP);

        } else if (value == 0) {
            return 1;

        } else {
            return 0;
        }
    }

    public int getWinner() {
        int winner = 0;
        double BestActivity = 0;

        for (int index = 0; index < neurons.size(); index++) {
            double action = neurons.get(index).getAction();

            if (action > BestActivity) {
                BestActivity = neurons.get(index).getAction();
                winner = index;
            }
        }

        return winner;
    }

    public void action(Entry en) {
        for (Neuron neuron : neurons) {
            double distance = 0;
            double distanceTitle = 0;

            for (int index = 0; index < en.getData().size(); index++) {
                double w1 = en.getData().get(index).getWeight();
                double w2 = neuron.getWeights().get(index);

                double dist = w1 - w2;
                distance = distance + pow(dist, 2);
            }

            for (int index2 = 0; index2 < en.getDataTitles().size(); index2++) {
                double w1 = en.getDataTitles().get(index2).getWeight();
                double w2 = neuron.getWeightsTitle().get(index2);

                double dist = w1 - w2;
                distanceTitle += pow((dist), 2);
            }

            distance = sqrt(distance + distanceTitle);
            neuron.setPotential(distance);
            neuron.setAction((1.0 / (1 + distance)));
        }
    }

    public void updateWeights(int winner, int entry) {
        Double learning;
        Double entryWeight;
        Double neuronWeight;

        for (int index = 0; index < neurons.size(); index++) {
            double neighbour = neighbourhoodFunction(abs(winner - index));
            int size = neurons.get(index).getWeights().size();

            for (int index2 = 0; index2 < size; index2++) {
                entryWeight = entries.get(entry).getData().get(index2).getWeight();
                neuronWeight = neurons.get(index).getWeights().get(index2);

                if (index == winner)
                    learning = (EPSILON * (entryWeight - neuronWeight));

                else
                    learning = (EPSILON * (entryWeight - neuronWeight) * neighbour);

                neurons.get(index).getWeights().set(index2, neurons.get(index).getWeights().get(index2) + learning);

                if (neurons.get(index).getWeights().get(index2) < -1)
                    neurons.get(index).getWeights().set(index2, -1.0);

                if (neurons.get(index).getWeights().get(index2) > 1)
                    neurons.get(index).getWeights().set(index2, 1.0);
            }

            int numTitles = neurons.get(index).getWeightsTitle().size();

            for (int index2 = 0; index2 < numTitles; index2++) {
                entryWeight = entries.get(entry).getDataTitles().get(index2).getWeight();
                neuronWeight = neurons.get(index).getWeightsTitle().get(index2);

                if (index == winner)
                    learning = (EPSILON * (entryWeight - neuronWeight));

                else
                    learning = (EPSILON * (entryWeight - neuronWeight) * neighbour);

                neurons.get(index).getWeightsTitle().set(index2, neurons.get(index).getWeightsTitle().get(index2) + learning);

                if (neurons.get(index).getWeightsTitle().get(index2) < 0)
                    neurons.get(index).getWeightsTitle().set(index2, 0.0);

                if (neurons.get(index).getWeightsTitle().get(index2) > 1)
                    neurons.get(index).getWeightsTitle().set(index2, 1.0);

            }
        }

    }

    public void clusterize() {
        int winner = 0;
        long startTime = System.currentTimeMillis();

        for (int index = 0; index < NUM_LEARN; index++) {
            if (index == 1)
                System.out.println("[KOHONEN: " + type.toUpperCase() + "] Estimated duration:" + (System.currentTimeMillis() - startTime) * NUM_LEARN / 60000 + " minutes");

            for (Entry entry : entries) {
                int pickedIndex = pickEntry();
                action(entries.get(pickedIndex));
                winner = getWinner();
                updateWeights(winner, pickedIndex);
            }

            System.out.println("[KOHONEN: " + type.toUpperCase() + "] Learning step " + (index + 1));
        }

        for (int index = 0; index < entries.size(); index++) {
            action(entries.get(index));
            winner = getWinner();

            clusters.get(winner).getDistance().add(neurons.get(winner).getPotential());
        }

        try {
            String disp = "";
            BufferedWriter writer2 = new BufferedWriter(
                new FileWriter("./Simulation/result/simtest.csv"));

            for (int index = 0; index < NUM_NEURONS; index++) {
                for (int index2 = 0; index2 < clusters.get(index).getRecipes().size(); index2++) {
                    disp = disp + clusters.get(index).getRecipes().get(index2).getName() + ";";
                }

                disp = disp + "\n";
            }

            writer2.write(disp);
            writer2.close();

        } catch (IOException e) {

        }
    }

    public LinkedList<Category> getClusters() {
        return clusters;
    }
}

