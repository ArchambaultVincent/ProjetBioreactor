package Optimisation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.util.ArrayList;

public class Genetic_Algorithm {

    private double Ph=0.0;
    private double Temp=0.0;
    private double DO2=0.0;
    private double PHfitness = 2; // PH
    private double Tempfitness = 2;// TEMP
    private double Dofitness =0.2; // DO2
    private int echantillion=2;

     ArrayList<Bio_Parameter> bio=new ArrayList<>();

     public void Add_Bio_Param(Bio_Parameter b){
         int index = 0;
             while( index < bio.size() && b.getBiomass() < bio.get(index).getBiomass() ) {
                index++;
             }
             if(index < bio.size()){
                bio.remove(index);
                bio.add(index,b);
             }
     }

    /**
     * Lis un fichier Csv pour récupéré des paramètres de culture
     * @param file
     * @return
     */
    public void ReadBio(String file) {
        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {
            // read line by line
            String line = null;
            while ((br.readLine()) != null) {
                line = br.readLine();
            }
            String linesplit[] = line.split(";");
            double Ph = Double.parseDouble(linesplit[0]);
            double Temp = Double.parseDouble(linesplit[4]);
            double DO2 = Double.parseDouble(linesplit[1]);
            double biomass = Double.parseDouble(linesplit[2]);
            Bio_Parameter bio_param = new Bio_Parameter(Ph, Temp, DO2, biomass);
            if (bio.isEmpty())
                bio.add(bio_param);
            else {
                if (bio.size() < echantillion) {
                        bio.add(bio_param);
                } else {
                    Add_Bio_Param(bio_param);
                }
            }
            br.close();
            reader.close();
        }catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

    }

    /**
     * récupère toute les données des cultures effectuer
     * @param path
     */
    public void Selection(String path){
        File dir = new File(path);
        System.out.println();
        File[] files = dir.listFiles();
        //on parcours tous les fichier du dossier
        for(File file : files) {
            // si il s'agit d'un fichier vcf ou ics on l'affiche
            if(file.getName().contains(".csv")) {
                ReadBio(file.getAbsolutePath());
            }
        }
    }

    /**
     *
     * @param b
     */
    public void Mutation(Bio_Parameter b){
        Ph=Math.max(Ph-PHfitness+Math.random()*((Ph+PHfitness)-(Ph-PHfitness)),0);
        Temp=Temp-Tempfitness+Math.random()*((Temp+Tempfitness)-(Temp-Tempfitness));
        DO2=Math.max(DO2-Dofitness+Math.random()*((DO2+Dofitness)-(DO2-Dofitness)),0);
        b.setDo2(DO2);
        b.setPh(Ph);
        b.setTemp(Temp);
    }

    /**
     * Selectionne les Genes pour la génération d'une nouvelle donnée
     * @param b1
     * @param b2
     * @return
     */
    public Bio_Parameter Gene_Selection(Bio_Parameter b1, Bio_Parameter b2){
        double rand = Math.random() ;
        if(rand > 0.5){
            return b1;
        }
        else{
            return b2;
        }
    }

    public ArrayList<Double> FitnessFunction(){
        ArrayList<Double> list =new ArrayList<>();
        double Maxbiomass=0;
        for(int index=0; index < bio.size();index++){
            Maxbiomass+=bio.get(index).getBiomass();
        }
        double proba=0.0;
        for(int index2=0; index2 < bio.size();index2++){
            proba += bio.get(index2).getBiomass()/Maxbiomass;
            list.add(proba);
        }
        return list;
    }

    /**
     * Mélange les données pour la génération d'une nouvelle entrée.
     * @param b1
     * @param b2
     * @return
     */
    public Bio_Parameter Crossover(Bio_Parameter b1, Bio_Parameter b2){
        Bio_Parameter bchoose = Gene_Selection(b1,b2);
        Ph=bchoose.getPh();
         bchoose = Gene_Selection(b1,b2);
        Temp=bchoose.getTemp();
         bchoose = Gene_Selection(b1,b2);
        DO2=bchoose.getDo2();
        Bio_Parameter b = new Bio_Parameter(Ph,Temp,DO2,0);
        return b;
    }

    /**
     *
     * @param path
     * @return
     */
    public Bio_Parameter Genetic_Algorithme(String path){
        Selection(path);
        int index1;
        int index2;
        ArrayList<Integer> list =new ArrayList<>();
        for(int index=0;index < echantillion ;index++)
            list.add(index);
        index1=(int)Math.round(Math.random()*(list.size()-1));
        index1=list.get(index1);
        list.remove(index1);
        index2=(int)Math.round(Math.random()*(list.size()-1));
        Bio_Parameter bm=Crossover(bio.get(index1),bio.get(index2));
        Mutation(bm);
        return bm;
    }

    public Bio_Parameter Randomizer(){
        Ph=Math.random()*8;
        Temp=13+Math.random()*(14);
        DO2=Math.random();
        Bio_Parameter b= new Bio_Parameter(Ph,Temp,DO2,0);
        return b;
    }
}
