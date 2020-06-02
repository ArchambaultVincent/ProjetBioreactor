import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.Perceptron;
import org.neuroph.util.TransferFunctionType;

import java.util.Arrays;

public class test_neuronalNetwork {
    public static void main(String[] args) {

// create training set (logical XOR function)
        DataSet trainingSet = new DataSet(4, 2);
        trainingSet.add(new DataSetRow(new double[]{0, 0,0,1}, new double[]{0,1}));
        trainingSet.add(new DataSetRow(new double[]{0,0, 1,0}, new double[]{0,1}));
        trainingSet.add(new DataSetRow(new double[]{1,0,0, 0}, new double[]{1,0}));
        trainingSet.add(new DataSetRow(new double[]{1, 1,0,0}, new double[]{0,0}));

// create multi layer perceptron
        MultiLayerPerceptron myMlPerceptron = new MultiLayerPerceptron(TransferFunctionType.TANH, 4, 3, 2);
// learn the training set
        myMlPerceptron.learn(trainingSet);

// test perceptron
        System.out.println("Testing trained neural network");
        testNeuralNetwork(myMlPerceptron, trainingSet);

// save trained neural network
        myMlPerceptron.save("myMlPerceptron.nnet");

// load saved neural network
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

// test loaded neural network
        System.out.println("Testing loaded neural network");
        testNeuralNetwork(loadedMlPerceptron, trainingSet);

    }

    public static void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {

        for (DataSetRow dataRow : testSet.getRows()) {
            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            //System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
            //System.out.println(" Output: " + Arrays.toString(networkOutput));
        }
    }
}