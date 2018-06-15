package pl.polsl.java.lab.Daniel.Kozlik;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains informations about recipes stored in {@link CookBookModel}
 *
 * @author Daniel Koźlik
 * @version 1.0
 */
public class Recipe {

    public Recipe() {
        ingredients = new ArrayList<>();
    }

    /**
     * Recipe name.
     */
    protected String name;
    /**
     * Stores ingredient objects.
     *
     * @see Ingredient
     * @see ArrayList
     */
    protected List<Ingredient> ingredients;
    /**
     * Specify how many ingredients match with parameters in {@link CookBookModel#getRecipes(java.lang.String...)
     * }.
     *
     * @see Ingredient
     */
    protected int matches;

    /**
     * Set {@link Recipe#name} as given parameter.
     *
     * @param name recipe name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter to name.
     *
     * @return {@link Recipe#name}
     */
    public String getName() {
        return name;
    }

    /**
     * Add given as parameter {@link Ingredient} to list.
     *
     * @param i new ingredient in list {@link Recipe#ingredients}
     */
    public void addIngredient(Ingredient i) {
        ingredients.add(i);
    }


    /**
     * Getter to ingredients.
     *
     * @return List of {@link Recipe#ingredients}
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Getter to matches.
     *
     * @return {@link Recipe#matches}
     */
    public int getMatches() {
        return matches;
    }

    /**
     * Set {@link Recipe#matches}
     *
     * @param matches how many matches we set.
     */
    public void setMatches(int matches) {
        this.matches = matches;
    }

}
