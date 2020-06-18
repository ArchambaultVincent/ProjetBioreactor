import Communication.Serveur;
import Simulator.Simulator;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class clientTest2   extends Thread {
    private static float do2 =  0.8f;
    private static float Co2  = 0f;
    private static float TEMP  = 27f;
    private static float PH = 9f;
    private static float debit = 7f;


    static byte[] createId() {
        int id = 0;
        byte[] format_id = new byte[2];
        if (id < 255) {
            format_id[0] = 0x00;
            BigInteger i = BigInteger.valueOf(id);
            format_id[1] = i.byteValue();
        } else {
            BigInteger i = BigInteger.valueOf(id);
            format_id = i.toByteArray();
            byte bito = format_id[1];
            format_id[1] = format_id[0];
            format_id[0] = bito;
        }
        id++;
        if (id > 99) {
            id = 0;
        }
        return format_id;
    }

    static byte[] createValue(float value, String commad) {
        byte[] format_id;
        if (commad.equals("S")) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.BYTES);
            byteBuffer.putInt((int) value);
            byteBuffer.flip();

            format_id = byteBuffer.array();

            for (int i = 0; i < format_id.length / 2; i++) {
                byte bito = format_id[i];
                format_id[i] = format_id[(format_id.length - 1) - i];
                format_id[(format_id.length - 1) - i] = bito;
            }
        } else {
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

    static byte[] createMessage(String command, float value) {
        float f = (float) value;
        byte[] message_byte = new byte[8];
        byte[] bytes = command.getBytes();
        byte[] id_byte = createId();
        byte[] value_byte = createValue(f, command);
        message_byte[0] = id_byte[0];
        message_byte[1] = id_byte[1];
        for (int index = 0; index < bytes.length; index++) {
            message_byte[index + 2] = bytes[index];
        }
        for (int index2 = bytes.length + 2; index2 < 7; index2++) {
            message_byte[index2] = value_byte[index2 - (bytes.length + 2)];
        }
        message_byte[7] = 0x0A;
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[message_byte.length * 2];
        for (int j = 0; j < message_byte.length; j++) {
            int v = message_byte[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        System.out.println(hexChars);
        // String message=new String(message_byte);
        return message_byte;
    }

    public static void readmsg(DataInputStream dis) throws IOException {
        byte[] reponse = new byte[8];
        byte[] subval = new byte[4];
        dis.read(reponse);
        subval[0] = reponse[6];
        subval[1] = reponse[5];
        subval[2] = reponse[4];
        subval[3] = reponse[3];
        ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
        byteBuffer.put(subval);
        byteBuffer.flip();
        float val = byteBuffer.getFloat();
        System.out.println(val);

    }

    public void run() {
        byte[] awnser = new byte[8];
        //Serveur serv= new Serveur("86.245.165.184",4200);

        ServerSocket sSocket = null;
        DataInputStream dis;
        DataOutputStream dout;
        try {
            sSocket = new ServerSocket(8888);
            Socket s = sSocket.accept();
            System.out.println("connected ! ");
            dis = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            System.out.println("Lecture");
            while (1 == 1) {
                byte[] message = new byte[8];
                dis.read(awnser);
                String command = new String(awnser, "UTF-8");
                //System.out.println(command);
                byte[] subval = new byte[4];
                subval[0] = awnser[6];
                subval[1] = awnser[5];
                subval[2] = awnser[4];
                subval[3] = awnser[3];
                ByteBuffer byteBuffer = ByteBuffer.allocate(Float.BYTES);
                byteBuffer.put(subval);
                byteBuffer.flip();
                float val = byteBuffer.getFloat();
                if (command.contains("REQST")) {
                    message=createMessage("P",PH);
                    dout.write(message);
                    message=createMessage("T",TEMP);
                    dout.write(message);
                    message=createMessage("A",debit);
                    dout.write(message);
                    message=createMessage("D",do2);
                    dout.write(message);
                    message=createMessage("S",6);
                    dout.write(message);
                    message=createMessage("N",0.0f);
                    dout.write(message);
                    message=createMessage("U",0.0f);
                    dout.write(message);
                } else {
                    switch (command.charAt(2)) {
                        case 'P':
                            PH = val;
                            dout.write(awnser);
                            break;
                        case 'T':
                            TEMP = val - 273;
                            dout.write(awnser);
                            break;
                        case 'D':
                            do2 = val;
                            dout.write(awnser);
                            break;
                        case 'A':
                            Co2 = val;
                            dout.write(awnser);
                            break;
                        case 'S':
                            debit = val;
                            dout.write(awnser);
                            break;
                    }
                }
            }
            } catch(IOException e){
                e.printStackTrace();
            }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        Simulator sim = new Simulator("simtest_", 0.1f, 10,50,PH , do2,TEMP,debit);
        Thread  thread= new clientTest2();
        thread.start();
        sim.addEvent("PH", "OFF", 25);
        for (int index2 = 0; index2 <= 50; index2++) {
            sim.setCo2(Co2);
            sim.setDo2(do2);
            sim.setTemp(TEMP);
            sim.setPh(PH);
            sim.SimulationStep();
            do2 = sim.getDo2();
            Co2 = (float) sim.getBiomass();
            TEMP = sim.getTemp()+273;
            PH = sim.getPh();
            debit=sim.getDebit_dair();
            Thread.sleep(5000);
        }
    }

}