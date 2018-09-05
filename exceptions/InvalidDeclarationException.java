package exceptions;

/**
 * This class represents an error thrown whenever an invalid declaration of method
 */
public class InvalidDeclarationException extends IOError {
    public InvalidDeclarationException(){
        super("ERROR: Invalid declaration of a method");
    }
}
