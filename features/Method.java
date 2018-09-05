package oop.ex6.features;

import java.util.ArrayList;
import java.util.Stack;

/**
 * A class which defines a method in the user's program
 */
public class Method extends Scope {
    private String name;
    private ArrayList<String> parametersTypes;
    private Stack<ConditionalBlock> openedBlocks = new Stack<ConditionalBlock>();

    /**
     * constructor
     * @param methodName the name of the method
     */
    public Method(String methodName){
        name = methodName;
    }

    /**
     * a setter for the parameter types
     * @param methodParametersTypes an arraylist with the parameter types
     */
    public void setParametersTypes(ArrayList<String> methodParametersTypes){
        parametersTypes = methodParametersTypes;
    }
    public static void validateName(){}

    /**
     * a getter for the
     * @return
     */
    public Stack<ConditionalBlock> getOpenedBlocks(){
        return openedBlocks;
    }
    public String getMethodName(){return name;}
    public ArrayList<String> getParametersTypes() {return parametersTypes;}
}
