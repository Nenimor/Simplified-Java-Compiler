package oop.ex6.main;

import exceptions.IOError;
import exceptions.InvalidArgumentsNumException;
import exceptions.SyntaxException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class contains the main method of the program.
 */
public class Sjavac {
    public static void main(String args[]) throws IOError, SyntaxException, IOException{
        // reading the file given as argument into an ArrayList, each cell in the arraylist is a line of code
        try{
            if (args.length == 0)
                throw new InvalidArgumentsNumException();
        }
        catch (InvalidArgumentsNumException e){
            System.err.println(e.getMessage());
        }

        File code = new File(args[0]);
        FileReader fileReader = new FileReader(code);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        ArrayList<String> commands = new ArrayList<>(0);
        int rowIndex = 0;
        while (bufferedReader.ready()){
            commands.add(rowIndex,bufferedReader.readLine());
            rowIndex++;
        }
        Processor processor = new Processor();

        try{
            for (String codeRow: commands){
                processor.compile(codeRow);
            }
            processor.finishCompilation();
        }
        catch (SyntaxException e) {
            return;
        }
        catch (IOError e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("0");
    }
}
