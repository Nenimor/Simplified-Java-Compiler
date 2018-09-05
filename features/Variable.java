package oop.ex6.features;


/**
 * A class which defines a variable in the user's program
 */
public class Variable {
    String name;
    String type;
    private boolean isAssigned;
    boolean isLocal;
    boolean isFinal;

    /**\
     * constructor
     * @param varName the name of the variable
     * @param varType the type of the variable
     * @param local boolean stating if the variable is local
     * @param finalIndicator boolean stating if the variable is final
     * @param assignedIndicator boolean stating if the variable has been assigned a value
     */
    public Variable(String varName, String varType, boolean local,boolean finalIndicator, boolean
            assignedIndicator){
        name = varName;
        type = varType;
        isLocal = local;
        isFinal = finalIndicator;
        isAssigned = assignedIndicator;
    }


    /**
     * a getter for the name of the variable
     * @return the name of the variable
     */
    public String getName(){
        return name;
    }

    /**
     * a getter for the type of the variable
     * @return the type of the variable
     */
    public String getType(){
        return type;
    }

    /**
     * a getter for the assignment state of the variable
     * @return true iff the variable has been assigned a value
     */
    public boolean isAssigned(){
        return isAssigned;
    }

    /**
     * changes the assignment state of the variable to true
     */
    public void markAsAssigned(){
        isAssigned = true;
    }
    public boolean isLocal(){return isLocal;}

    /**
     * returns the isFinal indicator
     * @return true iff the variable is final
     */
    public boolean isFinal(){
        return isFinal;
    }

}
