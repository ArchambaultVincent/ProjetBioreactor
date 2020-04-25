package DetectionErrorIA;

import com.ucp.ai_experiments.AIEntries;
import com.ucp.cookwithease.dao.DAOFactory;
import com.ucp.cookwithease.model.Recipe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;


public class AITest {
    public static void main(String[] args) throws Exception {
        LinkedList<Recipe> starters = DAOFactory.getRecipeDAO().findAllStarters();
        LinkedList<Recipe> mainCourses = DAOFactory.getRecipeDAO().findAllMainCourses();
        LinkedList<Recipe> desserts = DAOFactory.getRecipeDAO().findAllDesserts();

        LinkedList<String> startersIngredients = AIEntries.getAllStartersIngredients();
        LinkedList<String> mainCoursesIngredients = AIEntries.getAllMainCoursesIngredients();
        LinkedList<String> dessertsIngredients = AIEntries.getAllDessertsIngredients();

        LinkedList<String> startersNames = AIEntries.getPartsOfStartersName();
        LinkedList<String> mainCoursesNames = AIEntries.getPartsOfMainCoursesName();
        LinkedList<String> dessertsNames = AIEntries.getPartsOfDessertsName();

        KohonenThread kohonenTh1 = new KohonenThread(startersIngredients, starters, startersNames, "STARTERS");
        KohonenThread kohonenTh2 = new KohonenThread(mainCoursesIngredients, mainCourses, mainCoursesNames, "MAIN_COURSES");
        KohonenThread kohonenTh3 = new KohonenThread(dessertsIngredients, desserts, dessertsNames, "DESSERTS");

        kohonenTh1.start();
        kohonenTh2.start();
        kohonenTh3.start();

        kohonenTh1.join();
        kohonenTh2.join();
        kohonenTh3.join();

        Kohonen kohonen1 = kohonenTh1.getKohonen();
        Kohonen kohonen2 = kohonenTh2.getKohonen();
        Kohonen kohonen3 = kohonenTh3.getKohonen();

        try {
            BufferedWriter writer3 = new BufferedWriter(new FileWriter("./src/main/resources/SortiIA.csv"));
            PrintWriter print = new PrintWriter(writer3);
            String data = "";
            int indexCat = 0;

            for (Category cat : kohonen1.getClusters()) {
                data = data + indexCat + ";starter;";

                for (int indexdisp = 0; indexdisp < kohonen2.getClusters().size(); indexdisp++) {
                    data = data + cat.getDistanceCat(indexdisp) + ";";
                }

                if (!cat.getRecipes().isEmpty()) {
                    for (int indexdisp = 0; indexdisp < cat.getRecipes().size(); indexdisp++) {
                        data = data + cat.getRecipes().get(indexdisp).getId() + ";" + cat.getDistance().get(indexdisp) + ";";
                    }
                }

                data = data + "\n";
                indexCat++;
            }

            for (Category cat : kohonen2.getClusters()) {
                data = data + indexCat + ";main_course;";
                for (int indexdisp = 0; indexdisp < kohonen2.getClusters().size(); indexdisp++) {
                    data = data + cat.getDistanceCat(indexdisp) + ";";
                }

                if (!cat.getRecipes().isEmpty()) {
                    for (int indexdisp = 0; indexdisp < cat.getRecipes().size(); indexdisp++) {
                        data = data + cat.getRecipes().get(indexdisp).getId() + ";" + cat.getDistance().get(indexdisp) + ";";
                    }
                }

                data = data + "\n";
                indexCat++;
            }

            for (Category cat : kohonen3.getClusters()) {
                data = data + indexCat + ";dessert;";
                for (int indexdisp = 0; indexdisp < kohonen2.getClusters().size(); indexdisp++) {
                    data = data + cat.getDistanceCat(indexdisp) + ";";
                }

                if (!cat.getRecipes().isEmpty()) {
                    for (int indexdisp = 0; indexdisp < cat.getRecipes().size(); indexdisp++) {
                        data = data + cat.getRecipes().get(indexdisp).getId() + ";" + cat.getDistance().get(indexdisp) + ";";
                    }
                }

                data = data + "\n";
                indexCat++;
            }

            print.write(data);
            writer3.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        System.err.println("***********************************************");
        System.err.println("*               KOHONEN STARTERS              *");
        System.err.println("***********************************************");

        for (Category cat : kohonen1.getClusters()) {
            System.out.println("**************** NEXT CATEGORY ****************");

            for (Recipe recipe : cat.getRecipes()) {
                System.out.println(recipe.getName());
            }
        }

        System.err.println("***********************************************");
        System.err.println("*             KOHONEN MAIN COURSES            *");
        System.err.println("***********************************************");

        for (Category cat : kohonen2.getClusters()) {
            System.out.println("**************** NEXT CATEGORY ****************");

            for (Recipe recipe : cat.getRecipes()) {
                System.out.println(recipe.getName());
            }
        }

        System.err.println("***********************************************");
        System.err.println("*               KOHONEN DESSERS               *");
        System.err.println("***********************************************");

        for (Category cat : kohonen3.getClusters()) {
            System.out.println("**************** NEXT CATEGORY ****************");

            for (Recipe recipe : cat.getRecipes()) {
                System.out.println(recipe.getName());
            }
        }
    }
}
