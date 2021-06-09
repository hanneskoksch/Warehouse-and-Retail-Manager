package core.exceptions;

/**
 * Is thrown if the entered Values for an Item are not valid
 */
public class ValuesNotValidException extends Exception{

    String errorMessage;
    public ValuesNotValidException(String errorMessage) {
        super("The entered values for item are not valid: Message("+errorMessage+")");
        this.errorMessage = errorMessage;
    }
    public String getMessage(){
        return errorMessage;
    }
}