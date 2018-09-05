package exceptions;

/**
 * This class represents an exception. this exception is being thrown whenever a conditional statement was
 * a given a variable that isnt defined as boolean
 */
public class ExpectedBooleanException extends IOError {
    public ExpectedBooleanException(){
        super("ERROR: Expected a boolean in the conditional statement");
    }
}
