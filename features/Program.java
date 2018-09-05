package oop.ex6.features;

import java.util.ArrayList;

/**
 * This Class represents a Program. a program represents the program in a code file.
 */
public class Program {
    private ArrayList<Variable> globalVariables = new ArrayList<Variable>();
    private ArrayList<String> globalVariablesToCheck = new ArrayList<String>(); //global variables which were
    // assigned into local ones, and should be verified as global declared variables
    private ArrayList<ArrayList<String>> methodCalls = new ArrayList<ArrayList<String>>();
    private Method currentOpenedMethod;
    private ArrayList<Method> closedMethods = new ArrayList<Method>();

    /**
     * constructor of a Program object
     */
    public Program(){

    }

    /**
     * a getter for the global variables arraylist
     * @return the global variables arraylist
     */
    public ArrayList<Variable> getGlobalVariables(){
        return globalVariables;
    }

    /**
     * a getter for the arraylist of global variables which were assigned into local ones, and should be verified as
     * global declared variables.
     * @return arraylist of global variables that need to be checked
     */
    public ArrayList<String> getGlobalVariablesToCheck(){
        return globalVariablesToCheck;
    }

    /**
     * a getter for the method calls arraylist
     * @return method calls arraylist
     */
    public ArrayList<ArrayList<String>> getMethodCalls(){
        return methodCalls;
    }

    /**
     * a getter for the closed methods arraylist
     * @return the closed methods arraylist
     */
    public ArrayList<Method> getClosedMethods(){
        return closedMethods;
    }

    /**
     * a getter for the current opened method
     * @return the current opened method
     */
    public Method getCurrentOpenedMethod(){
        return currentOpenedMethod;
    }
    public void setCurrentOpenedMethod(Method method){
        currentOpenedMethod = method;
    }
    /**
     * a getter for the current scope
     * @return the current scope
     */
}

