package DetectionErrorIA;

public class TextAnalysis {
    private LinkedList<Entry> entries = new LinkedList<>();
    private LinkedList<PhWeight> maxIngredients = new LinkedList<>();

    public TextAnalysis(LinkedList<String> ingredients, LinkedList<Recipe> recipes, LinkedList<String> titles) {
        for (String ing : ingredients) {
            if (!ing.equals("poivre") && !ing.equals("sel")) {
                PhWeight iw = new PhWeight(ing);
                maxIngredients.add(iw);
            }
        }

        for (int index = 0; index < recipes.size(); index++) {
            LinkedList<PhWeight> iwlist = new LinkedList<>();
            LinkedList<PhWeight> twlist = new LinkedList<>();

            /* Create weights based on titles */
            for (String title : titles) {
                PhWeight tw = new PhWeight(title);
                twlist.add(tw);
            }

            /* Create ingredient weights */
            for (String ing : ingredients) {
                if (!ing.equals("poivre") && !ing.equals("sel")) {
                    PhWeight iw = new PhWeight(ing);
                    iwlist.add(iw);
                }
            }

            Entry en = new Entry(iwlist, twlist, recipes.get(index).getName());
            entries.add(en);
        }
    }

    /**
     * Function
     * V 1.0.0.1 : binary Weight 0 the ingredients isn't exist
     * Add Weight based on Title
     *
     * @param recipes : list of Recipes
     * @return all Recipes with each
     */
    public LinkedList<Entry> analyze(LinkedList<Recipe> recipes) {
        /* Initialize weights on titles */
        HashMap<String, Double> maxIngredientsQuantities = Constants.getMaxIngredientsQuantities();
        
        for (Entry en : entries) {
            for (int indexTitle = 0; indexTitle < en.getDataTitles().size(); indexTitle++) {
                if (AITools.contains(en.getRecipeName(), en.getDataTitles().get(indexTitle).getName()))
                    en.getDataTitles().get(indexTitle).setWeight(1.0);
            }
        }
        
        /* Initialize weights on ingredients */
        for (int index = 0; index < recipes.size(); index++) {
            for (Ingredient ing : recipes.get(index).getIngredients()) {
                if (ing.getCleanedName().isEmpty()) {
                    continue;
                }

                for (int index2 = 0; index2 < entries.get(index).getData().size(); index2++) {
                    if (entries.get(index).getData().get(index2).getName().equals(ing.getCleanedName())) {
                        /*
                        if (entries.get(index).getRecipeName().contains(ing.getCleanedName())) {
                            entries.get(index).getData().get(index2).setWeight(1.0);
                            continue;
                        }
                        */

                        double quantities = ing.getQuantity() / recipes.get(index).getPersons();
                        quantities = AITools.normalizeQuantity(quantities, ing.getUnit());

                        double weight;
                        weight = quantities / (maxIngredientsQuantities.get(ing.getCleanedName()) + 1);
                        entries.get(index).getData().get(index2).setWeight(weight);

                    }
                }
            }
        }

        return entries;
    }
}
