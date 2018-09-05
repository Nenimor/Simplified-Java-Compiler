package exceptions;

import java.io.IOException;

/**
 * This class represents an exception. this exception is being thrown whenever an error in assignment
 * of variable has occurred.
 */
public class NameInvalidException extends IOError {
    public NameInvalidException(){
        super("ERROR: Invalid Name for a method or variable");
    }
}
