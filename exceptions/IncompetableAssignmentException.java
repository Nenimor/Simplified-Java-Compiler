package exceptions;

/**
 * This class represents an exception. this exception is being thrown whenever the an error in assignment
 * of variable has occurred.
 */
public class IncompetableAssignmentException extends IOError {
    public IncompetableAssignmentException(){
        super("ERROR: Incompatible Assignment has occurred, check your variable assignments");
    }
}
