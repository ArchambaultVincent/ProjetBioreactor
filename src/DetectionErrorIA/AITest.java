package DetectionErrorIA;

import java.io.*;
import java.util.LinkedList;


public class AITest {
    public static void main(String[] args) throws Exception {

        LinkedList<Error> errors = new LinkedList<>();
        LinkedList<String> settings = new LinkedList<>();
        String type ="";
        String row;
        BufferedReader csvReader = new BufferedReader(new FileReader("./Simulation/result/simtest.csv"));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            Error err = new Error();
            settings.add(data[1]);
            type=data[2];
        }
        csvReader.close();
        KohonenThread kohonenTh1 = new KohonenThread(settings, type);

        kohonenTh1.start();

        kohonenTh1.join();

        Kohonen kohonen1 = kohonenTh1.getKohonen();

        try {
            BufferedWriter writer3 = new BufferedWriter(new FileWriter("./src/DetectionErrorIA/SortiIA.csv"));
            PrintWriter print = new PrintWriter(writer3);
            String data = "";


            print.write(data);
            writer3.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        for (Category cat : kohonen1.getClusters()) {
            System.out.println("**************** NEXT CATEGORY ****************");

            for (Error error : cat.getRecipes()) {
                System.out.println(error.getName());
            }
        }
    }
}
