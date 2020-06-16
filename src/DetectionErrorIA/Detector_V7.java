package DetectionErrorIA;

import Optimisation.Bio_Parameter;
import Simulator.SimulatorState;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.input.Max;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.TransferFunctionType;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Detector_V7 {

    private MultiLayerPerceptron myMlPerceptron=null;
    protected int nbLayout=0;
    protected int nbCategory=0;
    protected int nbEntry=0;
    protected Kohonen kohonen;
    protected DataSet trainingSet;
    protected int nbData=0;
    protected double seuil = 0.1E-5;
    protected ArrayList<ArrayList<Double>> listData  = new ArrayList<>();

    protected ArrayList<Double> MaxVal = new ArrayList<>();
    protected ArrayList<Double> MinVal = new ArrayList<>();
    protected ArrayList<Double> MaxBiomass= new ArrayList<>();
    protected ArrayList<Entry_Detector> entryTraining = new ArrayList<>();
    protected ArrayList<Double> Maxecart = new ArrayList<>();

    public Detector_V7(int nbLayout){
        this.nbLayout=nbLayout;
            MaxVal.add(0.0);
            MinVal.add(999.0);
    }

    /**
     * Effectue une Classification des erreurs
     * @throws InterruptedException
     */
    public void Kohonen_learning(String directory) throws InterruptedException {
        KohonenThread kohonenTh1 = new KohonenThread(directory);
        kohonenTh1.start();
        kohonenTh1.join();
        kohonen=kohonenTh1.getKohonen();
    }

    /**
     * charge le résultat du Kohonen
     */
    public void Load_Kohonen(){

    }

    /**
     * Chage l'apprentissage souhaité
     */
    public NeuralNetwork  load_network(String data){
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");
        return loadedMlPerceptron;
    }

    /**
     * Entraine est sauvegarde le résultat de l'apprentissage
     * @param Save fichier de sauvegarde du réseau de neuronne
     */
    public void train_network(String Save){
        myMlPerceptron=new  MultiLayerPerceptron(TransferFunctionType.TANH, nbEntry, nbLayout,nbCategory );
        myMlPerceptron.learn(trainingSet);
        System.out.println("learning Success !");
        myMlPerceptron.save("./Simulation/result/resultKohonen/"+Save+".nnet");
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("./Simulation/result/resultKohonen/"+Save+".csv"));
            String data=nbEntry+";"+nbCategory;
            for(int index=0;index < Maxecart.size() ; index++)
                data+=";"+ Maxecart.get(index);
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double moyenne(ArrayList<Double> array){
        double somme =0.0;
        for(double val : array){
            somme+= val;
        }
        return  somme/array.size();
    }

    public void Add_DataEntry(Bio_Parameter b,int time){
        listData.get(0).add(b.getPh());
        listData.get(1).add(b.getDo2());
        //listData.get(2).add(b.getBiomass());
        //listData.get(3).add(b.getSubstract());
        listData.get(2).add(b.getTemp());
        //listData.get(5).add((double)time);
    }

    public void Simulate_Entry(int time,int timemax,ArrayList<ArrayList<Double>> ListSim ){
        for(int i=time; i < timemax ; i++){
            for(int j=0; j < nbEntry ; j++){
                switch (j) {
                    case 2 :
                        ListSim.get(j).add(MaxBiomass.get(i));
                        break;

                    case 5 :
                        ListSim.get(j).add((double)i);
                        break;

                    default:
                        ListSim.get(j).add(listData.get(j).get(listData.get(j).size()-1));
                        break;
                }
            }
        }
    }

    /**
     *  Determine la catégorie
     */
    public int calculate_entry(Bio_Parameter b,int time,int timemax){
        nbData++;
            int cat = 0;
            double[] entry= new double[nbEntry];
            Add_DataEntry(b,time);
            if(nbData > 2) {
                for (int i = 0; i < nbEntry ;i++) {
                    entry[i]=abs(listData.get(i).get(nbData-1)-moyenne(listData.get(i))) /Maxecart.get(i);
                }
                System.out.println(" entry: " + Arrays.toString(entry));
        myMlPerceptron.setInput(entry);
        myMlPerceptron.calculate();
        double[] output = myMlPerceptron.getOutput();
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

    /**
     * Ajoute les données brutes des différents apprentissages
     * @param category
     * @param category_index
     * @param nbCategorie
     */
    public void Add_DataSet(Category category,int category_index,int nbCategorie){

        boolean first=true;
        double[] out=new double[nbCategorie];

        BufferedReader csvReader = null;
        String row;
        for(Entry entry : category.getCategory()){
            ArrayList<ArrayList<Double>> listDatatrain  = new ArrayList<>();
            nbEntry=entry.getData().size()-2;

            if(first){
                for(int index=0; index < nbCategorie;index++){
                    if(category_index==index)
                        out[index]=1;
                    else
                        out[index]=0;
                }

                first=false;
            }
            for(int index2=0; index2 < nbEntry ; index2++){
                listDatatrain.add(new ArrayList<>());
            }
            try {
                csvReader = new BufferedReader(new FileReader(entry.getName()));
                int time=0;
                while ((row = csvReader.readLine()) != null) {
                    String[] entry_split = row.split(";");
                    nbEntry =entry_split.length-2;
                    int Maxindex=0;
                    for (int i = 0; i < entry_split.length; i++) {
                        if(i != 2 && i != 3) {
                            double val = Double.parseDouble(entry_split[i]);
                            listDatatrain.get(Maxindex).add(val);
                            if (MaxVal.size() <= Maxindex + 1 || MaxVal.isEmpty()) {
                                MaxVal.add(0.0);
                            }
                            if (MinVal.size() <= Maxindex + 1 || MinVal.isEmpty()) {
                                MinVal.add(999.0);
                            }
                            if (MaxVal.get(Maxindex) < val) {
                                MaxVal.set(Maxindex, val);
                            }
                            if (MinVal.get(Maxindex) > val) {
                                MinVal.set(Maxindex, val);
                            }
                            Maxindex++;
                        }
                    }
                    time++;
                    if(listDatatrain.get(0).size() >3) {
                        ArrayList<ArrayList<Double>> listDatatrainput  = new ArrayList<>();
                        for(ArrayList<Double> clone : listDatatrain){
                            listDatatrainput.add((ArrayList<Double>) clone.clone());
                        }
                        double[]  in=new double[nbEntry];
                        for (int index2 = 0; index2 < nbEntry; index2++)
                            in[index2] = 0;
                        trainingSet.add(new DataSetRow(in, out));
                        Entry_Detector entry_detector=new Entry_Detector(listDatatrainput);
                        entryTraining.add(entry_detector);
                    }
                }
            }catch (IOException e){

            }

        }
    }

    public void reset_Data(){
        for(ArrayList<Double> data : listData){
            data.clear();
        }
        nbData=0;
    }

    public void Normalize_Entry(DataSetRow dataset , Entry_Detector entry){
        int size= entry.getListDatatrain().size();
        double[] in=dataset.getInput();
        for(int i=0 ; i < size;i++){
            ArrayList<Double> Value =  entry.getListDatatrain().get(i);
            in[i]=abs(entry.Get_LastValue(i)-moyenne(Value));
            if(in[i] < seuil){
                in[i]=0;
            }
                if(Maxecart.size() <= i){
                    Maxecart.add(in[i]);
                    if(Maxecart.get(i) == 0){
                        Maxecart.set(i,1.0);
                    }
                }
                else{
                    if(Maxecart.get(i) < in[i]){
                        Maxecart.set(i,in[i]);
                    }
            }
        }
    }

    public void Normalize_DataSet(){
        // on normalize les valeurs par rapport au maximum
        for(int index=0;index < trainingSet.size();index++){
            Normalize_Entry(trainingSet.get(index),entryTraining.get(index));
        }
        // on met les valeurs entre 0 et 1
        for(int index2=0;index2 < trainingSet.size();index2++){
            double[] in = trainingSet.get(index2).getInput();
            for(int index3=0; index3 < in.length ; index3++ ){
                in[index3]=in[index3]/ Maxecart.get(index3);
            }
            trainingSet.get(index2).setInput(in);
        }
    }

    public void suppression_data (int outputDesired) {
        int index = 0;
        while (index < trainingSet.size()) {
            double[] in = trainingSet.get(index).getInput();
            int index2=0;
            while( index2 < in.length && in[index2] == 0){
                index2++;
            }
            if(index2 == in.length){
                int cat=0;
                double[] out = trainingSet.get(index).getDesiredOutput();
                while(out[cat] != 1){
                    cat++;
                }
                if(outputDesired == cat){
                    trainingSet.remove(index);
                }
                else{
                    index++;
                }
            }
            else{
                index++;
            }
        }
    }

    public  void purge_entry(){
        int index=0;
        int[] ispurge= new int[nbCategory];
        for(int i=0; i < nbCategory ; i++) ispurge[i]=0;
        while(index < trainingSet.size()){
            System.out.println(index+" "+trainingSet.size());
            double[] in = trainingSet.get(index).getInput();
            int index2=0;
            while( index2 < in.length && in[index2] == 0){
                index2++;
            }
            if(index2 < in.length){
                int cat=0;
                double[] out = trainingSet.get(index).getDesiredOutput();
                while(out[cat] != 1){
                    cat++;
                }
                if(ispurge[cat] == 0) {
                    suppression_data(cat);
                    ispurge[cat]=1;
                }
                else{
                    index++;
                }
            }
            else {
                index++;
            }
        }
    }

    public void training_Init(){
        LinkedList<Category> categories = kohonen.getClusters();
        nbCategory = categories.size();
        trainingSet = new DataSet(nbEntry, nbCategory);
        for(int index=0; index < nbCategory ;index++)
            Add_DataSet(categories.get(index),index,nbCategory);
        for(int index2=0 ; index2 < nbEntry ;index2++)
            listData.add(new ArrayList<Double>());
        Normalize_DataSet();
        purge_entry();
        Disp_trainingSet();
    }


    public MultiLayerPerceptron getMyMlPerceptron() {
        return myMlPerceptron;
    }

    public DataSet getTrainingSet() {
        return trainingSet;
    }

    public void Disp_trainingSet(){
        System.out.println(trainingSet.size());
        for(DataSetRow data : trainingSet){
            System.out.println(" input: " + Arrays.toString(data.getInput()));
            System.out.println(" out: " + Arrays.toString(data.getDesiredOutput()));
        }
    }

}
