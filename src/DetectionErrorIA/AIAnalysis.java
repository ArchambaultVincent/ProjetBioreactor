package DetectionErrorIA;

import com.ucp.cookwithease.dao.DAOFactory;
import com.ucp.cookwithease.dao.general.RecipeDAO;
import com.ucp.cookwithease.model.Recipe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class AIAnalysis {

    public static void main(String[] args) throws Exception {
/*        BufferedReader reader = new BufferedReader(new FileReader("./SortiIA_1.csv"));
        String currentLine;
        LinkedList<Integer> IdRecipes = new LinkedList<>();
        while ((currentLine = reader.readLine()) != null) {
            String[] tokens = currentLine.split(";");
            if((tokens.length - 201 )> 20) {
                for (int index = 202; index < tokens.length; index += 2) {
                    IdRecipes.add(Integer.parseInt(tokens[index]));
                }

            LinkedList<Recipe> RecipeList = DAOFactory.getRecipeDAO().findAll(IdRecipes);
            System.err.println("******************************CATEGORIE NUM "+tokens[0]+"**************");
            for(Recipe recipe : RecipeList){
                System.out.println(recipe.getName());
               }
            }
        }*/
    }
}
