package exceptions;

import java.io.IOException;

/**
 * This class represents an exception. this exception is being thrown whenever the number or type of sent
 * method parameters don't match the actual method parameters.
 */
public class UnmatchedParametersException extends IOError {
    public UnmatchedParametersException(){
        super("ERROR: The given parameters don't match the method parameters");
    }
}
