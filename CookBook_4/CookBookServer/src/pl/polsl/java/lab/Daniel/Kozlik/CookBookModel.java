/* 
 * CookBook
 * @author Daniel Koźlik
 * @version 1.0
 */
package pl.polsl.java.lab.Daniel.Kozlik;

import java.util.*;

/**
 * Stores and manages recipes in {@link ArrayList}. Components can be added,
 * extracted in two ways or deleted.
 *
 * @author Daniel Koźlik
 * @version 1.0
 * @see Recipe
 * @see ArrayList
 */
public class CookBookModel {

    public CookBookModel(String name) {
        recipes = new ArrayList<>();
        cookbookName = name;
    }

    /**
     * Stores recipe objects.
     *
     * @see Recipe
     * @see ArrayList
     */
    protected List<Recipe> recipes;

    /**
     * CookBook name
     */
    final private String cookbookName;

    /**
     * Getter to name.
     *
     * @return name
     */
    String getName() {
        return cookbookName;
    }

    /**
     * Add recipe to {@link CookBookModel#recipes}.
     *
     * @param r reference to {@link Recipe} class object added to list
     * @see Recipe
     */
    public void addRecipe(Recipe r) {
        recipes.add(r);
    }

    /**
     * Check {@link CookBookModel#recipes} from first element to element called
     * like given as parameter String.
     *
     * @param recipeName name of searched {@link Recipe}
     * @return first found object.
     * @throws NullPointerException if searched recipe does not exist
     * @see Recipe
     */
    public Recipe getRecipe(String recipeName) {
        Recipe tmp_recipe = new Recipe();
        boolean exist = false;
        for (Recipe r : recipes) {
            if (r.getName().equals(recipeName)) {
                tmp_recipe = r;
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new NullPointerException();
        }
        return tmp_recipe;
    }

    /**
     * Check all {@link CookBookModel#recipes} components, looking for
     * {@link Recipe} with {@link Ingredient} called like given as parameter
     * name. All {@link Recipe}s met this requirement, are added to returning
     * list.
     *
     * @param ingredientsName ingredient name searched among recipes
     * @return list of found recipes.
     * @throws EmptyListException when returned list is empty
     */
    @SuppressWarnings("unchecked")
    public List<Recipe> getRecipes(String... ingredientsName) throws EmptyListException {
        List<Recipe> tmp_recipes = new ArrayList<>();
        for (Recipe r : recipes) {
            r.setMatches(0);
            int m = 0;
            for (Ingredient i : r.ingredients) {
                for (String s : ingredientsName) {
                    if (i.getName().equals(s)) {
                        m++;
                    }
                }
            }
            if (m > 0) {
                r.setMatches(m);
                tmp_recipes.add(r);
            }
        }
        if (tmp_recipes.isEmpty()) {
            throw new EmptyListException();
        }
        return tmp_recipes;
    }

    /**
     * Getter to {@link recipes}.
     *
     * @return recipes list.
     */
    public List<Recipe> getAllRecipes() {
        return this.recipes;
    }

    /**
     * Remove from {@link CookBookModel#recipes} recipe called like given as
     * parameter name, if exist.
     *
     * @param recipeName name of removed {@link Recipe}
     * @return true if recipe exist and can be removed or false if does not.
     */
    public boolean deleteRecipe(String recipeName) {
        int i = 0;
        boolean exist = false;
        for (Recipe r : recipes) {
            if (r.getName().equals(recipeName)) {
                exist = true;
                break;
            }
            i++;
        }
        if (exist) {
            recipes.remove(i);
            return true;
        } else {
            return false;
        }
    }

}
