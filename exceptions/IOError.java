package exceptions;

import java.io.IOException;

/**
 * This is a parent class for all IO exceptions in the program.
 */
public class IOError extends IOException {
    private final String errCode = "2";

    public IOError(String message){
        super(message);
        System.out.println(errCode);
    }
}
