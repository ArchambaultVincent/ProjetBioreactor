package DetectionErrorIA;

import Optimisation.Bio_Parameter;
import org.neuroph.core.NeuralNetwork;

import javax.swing.text.html.parser.Parser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static java.lang.Math.abs;

public class Detector_Interface extends Detector_V7 {

    NeuralNetwork loadedMlPerceptron;

    public Detector_Interface(int nbLayout) {
        super(nbLayout);
    }

    public void Load_Neuronal(String Save) throws IOException {
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("./Simulation/result/resultKohonen/" + Save + ".nnet");
        BufferedReader csvReader = new BufferedReader(new FileReader("./Simulation/result/resultKohonen/" + Save + ".csv"));
        int time=0;
        String row;
        if ((row = csvReader.readLine()) != null) {
            String[] entry_split = row.split(";");
            nbEntry= Integer.parseInt(entry_split[0]);
            nbCategory= Integer.parseInt(entry_split[1]);
        }
    }


    public int calculate_entry(Bio_Parameter b, int time, int timemax){
        nbData++;
        int cat = -1;
        double[] entry= new double[nbEntry];
        Add_DataEntry(b,time);
        if(nbData > 2) {
            for (int i = 0; i < nbEntry ;i++) {
                entry[i]=abs(listData.get(i).get(nbData-1)-moyenne(listData.get(i))) /Maxecart.get(i);
            }
            System.out.println(" entry: " + Arrays.toString(entry));
            loadedMlPerceptron.setInput(entry);
            loadedMlPerceptron.calculate();
            double[] output = loadedMlPerceptron.getOutput();
            System.out.println(" output : " + Arrays.toString(output));
            double max= output[0];
            for(int j=0; j<output.length;j++){
                if(max < output[j]){
                    max = output[j];
                    cat=j;
                }
            }
        }

        return  cat;
    }

}
