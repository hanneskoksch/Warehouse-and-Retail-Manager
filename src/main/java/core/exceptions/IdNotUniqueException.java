package core.exceptions;

/**
 * Is thrown if the Item ID is not unique
 */
public class IdNotUniqueException extends Exception{
    public IdNotUniqueException(String errorMessage) {
        super("Id not unique \nID has to be declared specifically for one item \n"+errorMessage);
    }
}
