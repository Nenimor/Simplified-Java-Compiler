package exceptions;

import java.io.IOException;

/**
 * This is a class for all syntax exception thrown in the program - corresponds to an illegal code
 */
public class SyntaxException extends IOException {
    private final String illegalCode = "1";
    public SyntaxException(){
        System.out.println(illegalCode);
    }
}
