package exceptions;

/**
 * This class represents an exception. this exception is being thrown whenever a variable was used before
 * being declared or declared but wasnt assigned
 */
public class VariableNotDeclaredException extends IOError {
    public VariableNotDeclaredException(){
        super("ERROR: Variable was used before being declared or was declared but wasn't assigned");
    }
}
