package exceptions;


/**
 * This class represents an exception. this exception is being thrown whenever a conditional statement was
 * used outside of a method scope
 */
public class InvalidUsageException extends IOError {
    public InvalidUsageException(){
        super("ERROR: used a conditional statement outside of a method scope");
    }
}
