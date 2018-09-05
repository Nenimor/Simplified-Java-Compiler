package exceptions;

/**
 * This class represents an exception thrown if the number of given arguments in main is invalid
 */
public class InvalidArgumentsNumException extends IOError {
    public InvalidArgumentsNumException(){
        super("ERROR: Invalid number of arguments given (not 1)");
    }
}
