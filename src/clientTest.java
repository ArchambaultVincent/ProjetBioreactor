import Simulator.Simulator;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class clientTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Simulator sim = new Simulator("simtest_", 0.1, 10,50,7 , 0.8,27);
        ServerSocket sSocket = new ServerSocket(8888);
        Socket s= sSocket.accept();
        System.out.println("connected ! ");
        DataInputStream dis=new DataInputStream(s.getInputStream());
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        for (int index2 = 0; index2 <= 50; index2++) {

            String  str=(String)dis.readUTF();
            System.out.println("message= "+str);
            sim.SimulationStep();
            if(str.contains("REQUEST")){
                double do2 = sim.getDo2();
                dout.writeUTF("D;"+do2);
                double Co2 = sim.getBiomass();
                dout.writeUTF("C;"+Co2);
                int TEMP = (int)sim.getTemp();
                dout.writeUTF("T;"+TEMP);
                int PH = (int)sim.getPh();
                dout.writeUTF("P;"+PH);
                dout.flush();
            }
            Thread.sleep(1000);
        }
    }
}
