package pl.polsl.java.lab.Daniel.Kozlik;

/**
 * Exception thrown when returning list in method {@link CookBookModel#getRecipes(java.lang.String...)} is empty.
 * Caught in {@link CookBookModel#getRecipes(java.lang.String...)}
 * @author Daniel Koźlik
 * @version 1.0
 */
public class EmptyListException extends Exception{

    EmptyListException(){}
}