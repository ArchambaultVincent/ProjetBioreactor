package Communication;

import Optimisation.Bio_Parameter;
import Optimisation.Genetic_Algorithm;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Serveur {
    private Socket connexion = null;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private int id=0;
    private static int count = 0;
    private String response = "";
    Bio_Parameter param =null;

    public Serveur(String host, int port){
        param=new Bio_Parameter(0,0,0,0);
        try {
            connexion = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String createId(){
        String format_id;
        if( id < 10){
            format_id="0"+id;
        }
        else{
            format_id=Integer.toString(id);
        }
        id++;
        if(id > 99){
            id=0;
        }
        return format_id;
    }

    String createMessage(String command,String value){
        String Id=createId();
        byte[] message_byte=new byte [8];
        byte[] bytes=command.getBytes();
        byte[] id_byte=Id.getBytes();
        byte[] value_byte=value.getBytes();
        message_byte[0]=id_byte[0];
        message_byte[1]=id_byte[1];
        for(int index=0;index<bytes.length;index++){
            message_byte[index+2]=bytes[index];
        }
        for(int index=0;index<value_byte.length;index++){
            message_byte[index+bytes.length+2]=value_byte[index];
        }
        message_byte[7]=0x0A;
        String message=new String(message_byte);
        return message;
    }

    public Bio_Parameter Get_ParamSim(){
        Send("K","");
        Analyse_Awnser();
        Send("L","");
        Analyse_Awnser();
        Send("M","");
        Analyse_Awnser();
        Send("O","");
        Analyse_Awnser();
        Send("Q","");
        Analyse_Awnser();
        Bio_Parameter b = new Bio_Parameter(param.getPh(),param.getTemp(),param.getDo2(),param.getBiomass());
        return param;
    }



    //méthode d'envoie d'un message
    public String Send(String type,String value){
            try {
                writer = new PrintWriter(connexion.getOutputStream(), true);
                reader = new BufferedInputStream(connexion.getInputStream());
                String message=createMessage(type,value);
                writer.flush();
                writer.write(message);
                writer.flush();
                response=read();
                while(response.isEmpty()) {
                    response = read();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return response;
    }

    public void Analyse_Awnser(){
        double val=0.0;
        if(!response.isEmpty()) {
            switch (response.charAt(0)) {
                case 'P' :
                    val=Double.parseDouble(response.substring(1));
                    param.setPh(val);
                    break;
                case 'T' :
                    val=Double.parseDouble(response.substring(1));
                    param.setTemp(val);
                    break;
                case 'A' :
                    val=Double.parseDouble(response.substring(1));
                    param.setPh(val);
                    break;
                case 'D' :
                    val=Double.parseDouble(response.substring(1));
                    param.setDo2(val);
                    break;
                case 'N' :
                    val=Double.parseDouble(response.substring(1));
                    param.setPh(val);
                    break;
            }
        }
    }

    public Bio_Parameter Get_Param(){
        Send("REQUEST","");
        try{
            read();
            Analyse_Awnser();
            read();
            Analyse_Awnser();
            read();
            Analyse_Awnser();
            read();
            Analyse_Awnser();
            read();
            Analyse_Awnser();
            read();
            Analyse_Awnser();
        }
        catch (IOException e){

        }
        return param;
    }

    //Méthode pour lire les réponses du client
    private String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[8];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

    String getLastResponse(){
        return response;
    }
}
