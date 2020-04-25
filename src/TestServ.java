import Communication.Serveur;

public class TestServ {
    public static void main(String[] args) {
        //Serveur serv= new Serveur("localhost",9000);
        String test="anticonstitutionnelement";
        char decomposition=test.charAt(0);
        System.out.println(decomposition);
        System.out.println( test.substring(1));

    }
}
