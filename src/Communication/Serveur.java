package Communication;

import Optimisation.Bio_Parameter;
import Optimisation.Genetic_Algorithm;

import java.io.*;
import java.lang.reflect.Parameter;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class Serveur {
    private Socket connexion = null;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private int id=0;
    private static int count = 0;
    private byte[] response = new byte[8];
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

    byte[] createId(){
        byte[] format_id=new byte[2];
        if( id < 255){
            format_id[0]=0x00;
            BigInteger i = BigInteger.valueOf(id);
            format_id[1]=i.byteValue();
        }
        else{
            BigInteger i = BigInteger.valueOf(id);
            format_id=i.toByteArray();
            byte bito=format_id[1];
            format_id[1]=format_id[0];
            format_id[0]=bito;
        }
        id++;
        if(id > 99){
            id=0;
        }
        return format_id;
    }

    byte[] createValue(float value,String commad){
        byte[] format_id;
        if(commad.equals("S")){
            ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
            byteBuffer.putInt((int) value);
            byteBuffer.flip();

            format_id = byteBuffer.array();

            for (int i = 0; i < format_id.length / 2; i++) {
                byte bito = format_id[i];
                format_id[i] = format_id[(format_id.length - 1) - i];
                format_id[(format_id.length - 1) - i] = bito;
            }
        }
        else {
            ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
            byteBuffer.putFloat(value);
            byteBuffer.flip();
            format_id = byteBuffer.array();
            for (int i = 0; i < format_id.length / 2; i++) {
                byte bito = format_id[i];
                format_id[i] = format_id[(format_id.length - 1) - i];
                format_id[(format_id.length - 1) - i] = bito;
            }

        }
        return format_id;
    }

    byte[] createMessage(String command,float value){
        float f= value ;
        byte[] message_byte=new byte [8];
        byte[] bytes=command.getBytes();
        byte[] id_byte=createId();
        byte[] value_byte=createValue(f,command);
        message_byte[0]=id_byte[0];
        message_byte[1]=id_byte[1];
        for(int index=0;index<bytes.length;index++){
            message_byte[index+2]=bytes[index];
        }
        for(int index2=bytes.length+2;index2<7;index2++){
            message_byte[index2]=value_byte[index2-(bytes.length+2)];
        }
        message_byte[7]=0x0A;
        final  char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[message_byte.length * 2];
        for ( int j = 0; j < message_byte.length; j++ ) {
            int v = message_byte[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        System.out.println(hexChars);
        String message=new String(message_byte);
        System.out.println(message);
        return message_byte;
    }

    public Bio_Parameter Get_ParamSim() throws UnsupportedEncodingException {
        Send("K",0.0f);
        Analyse_Awnser();
        Send("L",0.0f);
        Analyse_Awnser();
        Send("M",0.0f);
        Analyse_Awnser();
        Send("O",0.0f);
        Analyse_Awnser();
        Send("Q",0.0f);
        Analyse_Awnser();
        Bio_Parameter b = new Bio_Parameter(param.getPh(),param.getTemp(),param.getDo2(),param.getBiomass());
        return param;
    }



    //méthode d'envoie d'un message
    public void Send(String type,float value){
            try {
                DataOutputStream writer =new DataOutputStream(connexion.getOutputStream());
                reader = new BufferedInputStream(connexion.getInputStream());
                byte[] message=createMessage(type,value);
                writer.flush();
                writer.write(message);
                response=read();
                Analyse_Awnser();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
    }

    public void Send_sim(String type,float value){
        try {
            DataOutputStream writer =new DataOutputStream(connexion.getOutputStream());

            byte[] message=createMessage(type,value);
            writer.flush();
            writer.write(message);
            writer.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void Analyse_Awnser() throws UnsupportedEncodingException {
        double val=0.0;
        byte[] subval = new byte[4];
        String command = new String(response, "UTF-8");
        System.out.println(command);
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
            switch (command.charAt(2)) {
                case 'P' :
                    subval[0]= response[6];
                    subval[1]= response[5];
                    subval[2]= response[4];
                    subval[3]= response[3];
                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    val= byteBuffer.getFloat();
                    param.setPh(val);
                    break;
                case 'T' :
                    subval[0]= response[6];
                    subval[1]= response[5];
                    subval[2]= response[4];
                    subval[3]= response[3];
                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    val= byteBuffer.getFloat();
                    param.setTemp(val-273);
                    break;
                case 'O' :
                    subval[0]= response[6];
                    subval[1]= response[5];
                    subval[2]= response[4];
                    subval[3]= response[3];
                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    val= byteBuffer.getFloat();
                    param.setCo(val/100);
                    break;
                case 'C' :
                    subval[0]= response[6];
                    subval[1]= response[5];
                    subval[2]= response[4];
                    subval[3]= response[3];

                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    val= byteBuffer.getFloat();
                    param.setCo(val/100);
                    break;
                case 'A' :
                    subval[0]= response[6];
                    subval[1]= response[5];
                    subval[2]= response[4];
                    subval[3]= response[3];

                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    val= byteBuffer.getFloat();
                    param.setDebit(val);
                    break;
                case 'D' :
                    subval[0]= response[6];
                    subval[1]= response[5];
                    subval[2]= response[4];
                    subval[3]= response[3];

                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    val= byteBuffer.getFloat();
                    param.setDo2(val/100);
                    break;
                case 'N' :
                    subval[0]= response[6];
                    subval[1]= response[5];
                    subval[2]= response[4];
                    subval[3]= response[3];

                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    val= byteBuffer.getFloat();
                    break;
            }
        System.out.println(val);
    }

    public Bio_Parameter Get_Param(){
        Send("REQST",0.0f);
        try{
            response=read();
            Analyse_Awnser();
            response=read();
            Analyse_Awnser();
            response=read();
            Analyse_Awnser();
            response=read();
            Analyse_Awnser();
            response=read();
            Analyse_Awnser();
            response=read();
            Analyse_Awnser();
            response=read();
            Analyse_Awnser();
        }
        catch (IOException e){

        }
        return param;
    }

    public void init_reader() throws IOException {
        reader = new BufferedInputStream(connexion.getInputStream());
    }
    //Méthode pour lire les réponses du client
    public byte[] read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[8];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        final  char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[b.length * 2];
        for ( int j = 0; j < b.length; j++ ) {
            int v = b[j] & 0xFF;
            hexChars[j * 2] =  hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        System.out.println(hexChars);

        return b;
    }

    //String getLastResponse(){
      //  return response;
    //}
}
