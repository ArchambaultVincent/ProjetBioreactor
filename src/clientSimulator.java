import Communication.Serveur;
import Simulator.Simulator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class clientSimulator extends Thread{

    private static float do2 =  0.8f;
    private static float Co2  = 0f;
    private static float TEMP  = 27f;
    private static float PH = 9f;
    private static float debit = 7f;

    public void run() {
        byte[] awnser;
        //Serveur serv= new Serveur("86.245.165.184",4200);
        Serveur  serv = new Serveur("86.245.165.184",4200);
        System.out.println("connection établie !");
        try {
            serv.init_reader();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(1 == 1){
            try {
                System.out.println("Lecture");
                awnser=serv.read();
                String command = new String(awnser, "UTF-8");
                //System.out.println(command);
                    byte[] subval = new byte[4];
                    subval[0]= awnser[6];
                    subval[1]= awnser[5];
                    subval[2]= awnser[4];
                    subval[3]= awnser[3];
                    ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
                    byteBuffer.put(subval);
                    byteBuffer.flip();
                    float val= byteBuffer.getFloat();
                    switch (command.charAt(2)){
                        case 'P' :
                            PH=val;
                            break;
                        case 'T' :
                            TEMP=val-273;
                            break;
                        case 'D' :
                            do2=val;
                            break;
                        case 'A' :
                            Co2=val;
                            break;
                        case 'S' :
                            debit=val;
                            break;
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Simulator sim = new Simulator("simtest_", 0.1f, 10,50,PH , do2,TEMP,debit);
        Thread  thread= new clientSimulator();
        thread.start();
        Serveur  servWrite = new Serveur("86.245.165.184",4200);
        System.out.println("connection write réussi");
        for (int index2 = 0; index2 <= 50; index2++) {
            sim.setCo2(Co2);
            sim.setDo2(do2);
            sim.setTemp(TEMP);
            sim.setPh(PH);
            sim.SimulationStep();
         //   String command = new String(response, "UTF-8");
                 do2 = sim.getDo2();
                 Co2 = (float) sim.getBiomass();
                 TEMP = sim.getTemp()+273;
                 PH = sim.getPh();
                 debit=sim.getDebit_dair();
            servWrite.Send("M",do2*100);
            servWrite.Send("O",Co2*100);
            servWrite.Send("L",TEMP);
            servWrite.Send("K",PH);
           // servWrite.Send("V",debit);
            TEMP = TEMP-273;
            Thread.sleep(5000);
            System.out.println("DO="+do2+"C0="+Co2+"TEMP="+TEMP+"PH="+PH);
        }
    }
}
