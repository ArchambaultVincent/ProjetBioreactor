package Optimisation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Genetic_Algorithm_V2 {
    private Bio_Parameter bm=null;
    private double Ph=0.0;
    private double Temp=0.0;
    private double DO2=0.0;
    private double PHfitness = 0.5; // PH
    private double Tempfitness = 2;// TEMP
    private double Dofitness =0.2; // DO2
    private int echantillion=20;
    private double biomass=0.0;
    private double biomass_rate=0.0;
    private boolean Init=false;
    int time=0;
     ArrayList<Bio_Parameter> bio=new ArrayList<>();

     public void Add_Bio_Param(Bio_Parameter b){
             bio.add(b);
             int index = 0;
             int indexmin=0;
             double min=bio.get(0).getBiomass();
             while( index < bio.size()) {
                 if(bio.get(index).getBiomass() < min){
                     indexmin=index;
                     min=bio.get(index).getBiomass();
                 }
                index++;
             }
             bio.remove(indexmin);
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
        int GeneMuta=(int)Math.random()*5;
        switch(GeneMuta) {
            case 0:
            Ph = Math.max(Ph - PHfitness + Math.random() * ((Ph + PHfitness) - (Ph - PHfitness)), 0);
            break;
            case 1:
            Temp = Temp - Tempfitness + Math.random() * ((Temp + Tempfitness) - (Temp - Tempfitness));
            break;
            case 2:
            DO2 = Math.max(DO2 - Dofitness + Math.random() * ((DO2 + Dofitness) - (DO2 - Dofitness)), 0);
            break;
            default:
            break;
        }
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
        double RateTotal = b1.getBiomass()+b2.getBiomass();
        double rand = Math.random() ;
        if(rand > b1.getBiomass()/RateTotal){
            return b1;
        }
        else{
            return b2;
        }
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


    public int Selection_BioParameter(ArrayList<Double> a1){
        int index=0;
        double proba=Math.random();
        while (proba < a1.get(index) && index < a1.size()) {
            proba += a1.get(index);
            index++;
        }
        return index;
    }

    /**
     *
     *
     * @return
     */
    public Bio_Parameter Genetic_Algorithme(double biomass,int time){
        if(bm != null){
            if(Init==false){
                Init=true;
                this.time=time;
                this.biomass=biomass;
            }
            else{
                this.biomass_rate=(biomass-this.biomass);
                this.biomass=biomass;
                bm.setBiomass(this.biomass_rate);
                System.out.println(" DO2=" + bm.getDo2() + " TEMP=" + bm.getTemp() + " PH=" + bm.getPh() + " RATE=" + biomass_rate);
            }
            if (bio.size() < echantillion) {
                bio.add(bm);
            } else {
                Add_Bio_Param(bm);
            }
        }
        ArrayList<Double> list =new ArrayList<>();
        double Proba=0.0;
        double Somme_biomass=0.0;
        for(int index3=0; index3 < bio.size() ; index3++){
            Somme_biomass+=bio.get(index3).getBiomass();
        }
        for(int index=0;index < bio.size() ;index++) {
            Proba+=bio.get(index).getBiomass()/Somme_biomass;
            list.add(Proba);
        }
        if(bio.size() >= echantillion){
            int index1;
            int index2;
            index1=Selection_BioParameter(list);
            
            list.remove(index1);
            index2=(int)Math.round(Math.random()*(list.size()-1));
            bm=Crossover(bio.get(index1),bio.get(index2));
            Mutation(bm);
        }
        else{
            bm=Randomizer();
        }
        return bm;
    }

    public Bio_Parameter Randomizer(){
        Ph=Math.random()*8;
        Temp=13+Math.random()*(14);
        DO2=Math.random();
        Bio_Parameter b= new Bio_Parameter(Ph,Temp,DO2,0);
        return b;
    }

    public Bio_Parameter Get_Best_Param(){
        int index = 0;
        int indexmax=0;
        double max=bio.get(0).getBiomass();
        while( index < bio.size()) {
            if(bio.get(index).getBiomass() > max){
                indexmax=index;
                max=bio.get(index).getBiomass();
            }
            index++;
        }
        return bio.get(indexmax);
    }
}