package exceptions;

/**
 * This class represents an exception. this exception is being thrown whenever the user used a method that
 * wasn't defined
 */
public class MethodNotDefinedException extends IOError {
    public MethodNotDefinedException(){
        super("ERROR: Used a method that wasn't defined");
    }
}
