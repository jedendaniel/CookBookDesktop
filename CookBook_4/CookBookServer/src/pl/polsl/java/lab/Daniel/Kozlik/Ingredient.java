package pl.polsl.java.lab.Daniel.Kozlik;

/**
 * The smallest part of cook book. Stored in {@link Recipe#ingredients}
 *
 * @author Daniel Koźlik
 * @version 1.0
 */
public class Ingredient {

    protected String name;

    Ingredient(String name){
        this.name = name;
    }
    /**
     * Set ingredient name.
     *
     * @param name entered name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return ingredient name
     */
    public String getName() {
        return name;
    }

}
