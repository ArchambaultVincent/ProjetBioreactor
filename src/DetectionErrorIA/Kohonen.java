package DetectionErrorIA;

import Simulator.Simulator;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.*;


public class Kohonen {
    private static final int DVP = 7;
    private static final int DVN = 9;
    private static final double EPSILON = 0.5;
    private static final double ALPHA = 0.125;
    private static final double BETA = 0.125;
    private static final int NUM_NEURONS = 50;
    private static final int NUM_LEARN = 500;

    private String type;
    private LinkedList<Neuron> neurons;
    private LinkedList<Entry> entries;
    private LinkedList<Category> clusters;
    private LinkedList<String> titles;
    private LinkedList<Integer> pickedEntries;

    public Kohonen(String directory) {
        this.type = type;
        this.clusters = new LinkedList<>();
        this.neurons = new LinkedList<>();
        this.entries = new LinkedList<>();
        this.pickedEntries = new LinkedList<>();
        initEntries(directory);
        Normalize_Entry();
        initWeights();
    }

    /**
     *  Definit l'air sous la courbe d'un paramètre
     * @return aire sous la courbe
     */
    private double Simpson(ArrayList<Double> function){
        double somme=0.0;
        int i =0;
        for ( i =0 ; i < function.size() ;i++){
            somme=(function.get(0)+function.get(function.size()-1))/2+2*function.get(round(1+1/2));
        }
        return somme +i/3;
    }

    private void initEntry(String FileName){
        BufferedReader csvReader = null;
        String row;
        Boolean init =true;
        LinkedList<Double> data_format=new LinkedList<>();
        ArrayList<ArrayList<Double>> data_Nformat = new ArrayList<>();
        try {
            csvReader = new BufferedReader(new FileReader(FileName));
            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                if(init){
                    for (int i = 0; i < data.length; i++)
                        data_Nformat.add(new ArrayList<>());
                    init=false;
                }
                for (int j = 0; j < data.length; j++) {
                    double value= Double.parseDouble(data[j]);
                    data_Nformat.get(j).add(value);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       for(int k = 0 ; k < data_Nformat.size();k++){
            double simpson_value = Simpson(data_Nformat.get(k));
            data_format.add(simpson_value);
        }
        Entry entry = new Entry(data_format,FileName);
       entries.add(entry);
    }

    private void initEntries(String directory){
        File dir = new File(directory);
        File[] files = dir.listFiles();
        //on parcours tous les fichier du dossier
        for(File file : files) {
            // si il s'agit d'un fichier vcf ou ics on l'affiche
            if(file.getName().contains(".csv")) {
                initEntry(file.getAbsolutePath());
            }
        }
    }

    private void Normalize_Entry(){
        LinkedList<Double> MaxArray= (LinkedList<Double>) entries.get(0).getData().clone();
        for(int index=1 ; index < entries.size(); index++){
            for(int index2=0; index2 < entries.get(index).getData().size();index2++){
                if(entries.get(index).getData().get(index2) > MaxArray.get(index2)){
                    MaxArray.set(index2,entries.get(index).getData().get(index2));
                }
            }
        }
        for(int index3=0 ; index3 < entries.size(); index3++) {
            for(int index4=0 ; index4 < entries.get(index3).getData().size(); index4++) {
                double val= entries.get(index3).getData().get(index4);
                double maxlocal=MaxArray.get(index4);
                entries.get(index3).getData().set(index4,val/maxlocal);
            }
        }
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

    public void initWeights() {
        if(entries.size() > 0) {
            for (int index = 0; index < NUM_NEURONS; index++) {
                Neuron neuron = new Neuron();
                Category category = new Category(NUM_NEURONS, index);

                for (int index2 = 0; index2 < entries.get(0).getData().size(); index2++) {
                    double val = random();
                    neuron.getWeights().add(val);
                }

                neurons.add(neuron);
                clusters.add(category);
            }
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


            for (int index = 0; index < en.getData().size(); index++) {
                double w1 = en.getData().get(index);
                double w2 = neuron.getWeights().get(index);

                double dist = w1 - w2;
                distance = distance + pow(dist, 2);
            }

            distance = sqrt(distance);
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
                entryWeight = entries.get(entry).getData().get(index2);
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
        }

    }

    public void clusterize() {
        int winner = 0;
        long startTime = System.currentTimeMillis();

        for (int index = 0; index < NUM_LEARN; index++) {
            if (index == 1)
                System.out.println("[KOHONEN: " +  "] Estimated duration:" + (System.currentTimeMillis() - startTime) * NUM_LEARN / 60000 + " minutes");

            for (Entry entry : entries) {
                int pickedIndex = pickEntry();
                action(entries.get(pickedIndex));
                winner = getWinner();
                updateWeights(winner, pickedIndex);
            }

            System.out.println("[KOHONEN: " +  "] Learning step " + (index + 1));
        }

        for (int index = 0; index < entries.size(); index++) {
            action(entries.get(index));
            winner = getWinner();
            clusters.get(winner).getDistance().add(neurons.get(winner).getPotential());
            clusters.get(winner).getCategory().add(entries.get(index));
        }

        try {
            String disp = "";
            BufferedWriter writer2 = new BufferedWriter(
                new FileWriter("./Simulation/result/resultKohonen/simtest.csv"));

            for (int index = 0; index < NUM_NEURONS; index++) {
                for (int index2 = 0; index2 < clusters.get(index).getCategory().size(); index2++) {
                    disp = disp + clusters.get(index).getCategory().get(index2).getName() + ";";
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

