package oop.ex6.features;

import java.util.ArrayList;

/**
 * This class is a parent class of the scopes in the program.
 */
public class Scope {
    private ArrayList<Variable> localVariables = new ArrayList<Variable>();
    private boolean isOpen = true;

    public Scope(){}

    /**
     * a getter for the local variables
     * @return an arraylist of the local variables
     */
    public ArrayList<Variable> getLocalVariables(){
        return localVariables;
    }

    /**
     * checkes if the scope is opened or not
     * @return true iff the scope is open, false else.
     */
    public boolean isOpen(){
        return isOpen;
    }

    /**
     * closes a scope
     */
    public void closeScope(){
        isOpen = false;
    }
}
