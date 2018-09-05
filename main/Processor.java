package oop.ex6.main;


import exceptions.*;
import oop.ex6.features.*;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;

/**
 * the main processor of the compiler - has different patterns and methods for each possible line in the
 * given code
 */
public class Processor {
    private static final int FINAL_LENGTH = 5 , VOID_LENGTH=4;
    public static boolean openedMethodIndicator = false;
    private Program programScope = new Program();
    private boolean isLastLineReturn = false; // indicates whether the previous line was "return;" or not


    public void compile(String codeRow) throws IOError, InvalidDeclarationException, SyntaxException{
        Matcher commentMatcher = Patterns.comment.matcher(codeRow);
        Matcher emptyLineMatcher = Patterns.emptyLine.matcher(codeRow);

        Matcher singleIntAssignmentMatcher = Patterns.singleIntAssignment.matcher(codeRow);
        Matcher finalIntDeclarationMatcher = Patterns.finalIntDeclaration.matcher(codeRow);

        Matcher singleDoubleAssignmentMatcher = Patterns.singleDoubleAssignment.matcher(codeRow);
        Matcher finalDoubleDeclarationMatcher = Patterns.finalDoubleDeclaration.matcher(codeRow);

        Matcher singleStringAssignmentMatcher = Patterns.singleStringAssignment.matcher(codeRow);
        Matcher finalStringDeclarationMatcher = Patterns.finalStringDeclaration.matcher(codeRow);

        Matcher singleCharAssignmentMatcher = Patterns.singleCharAssignment.matcher(codeRow);
        Matcher finalCharDeclarationMatcher = Patterns.finalCharDeclaration.matcher(codeRow);

        Matcher singleBooleanAssignmentMatcher = Patterns.singleBooleanAssignment.matcher(codeRow);
        Matcher finalBooleanDeclarationMatcher = Patterns.finalBooleanDeclaration.matcher(codeRow);

        Matcher intDeclarMatcher = Patterns.singleIntDeclaration.matcher(codeRow);
        Matcher boolDeclarMatcher = Patterns.singleBooleanDeclaration.matcher(codeRow);
        Matcher doubleDeclarMatcher = Patterns.singleDoubleDeclaration.matcher(codeRow);
        Matcher stringDeclarMatcher = Patterns.singleStringDeclaration.matcher(codeRow);
        Matcher charDeclarMatcher = Patterns.singleCharDeclaration.matcher(codeRow);

        Matcher multiIntDeclarMatcher = Patterns.multiIntVariable.matcher(codeRow);
        Matcher multiBoolDeclarMatcher = Patterns.multiBooleanVariable.matcher(codeRow);
        Matcher multiStringDeclarMatcher = Patterns.multiStringVariable.matcher(codeRow);
        Matcher multiCharDeclarMatcher = Patterns.multiCharVariable.matcher(codeRow);
        Matcher multiDoubelDeclarMatcher = Patterns.multiDoubleVariable.matcher(codeRow);

        Matcher boolAssignMatcher = Patterns.booleanAssignment.matcher(codeRow);
        Matcher intAssignMatcher = Patterns.intAssignment.matcher(codeRow);
        Matcher stringAssignMatcher = Patterns.stringAssignment.matcher(codeRow);
        Matcher charAssignMatcher = Patterns.charAssignment.matcher(codeRow);
        Matcher doubleAssignMatcher = Patterns.doubleAssignment.matcher(codeRow);

        Matcher multiParamMethodMatcher = Patterns.multiParamMethod.matcher(codeRow);
        Matcher singleParamMethodMatcher = Patterns.singleParamMethod.matcher(codeRow);
        Matcher noParamMethodMatcher = Patterns.noParamMethod.matcher(codeRow);

        Matcher multiParamCallMatcher = Patterns.multiParamMethodCall.matcher(codeRow);
        Matcher singleParamCallMatcher = Patterns.singleParamMethodCall.matcher(codeRow);
        Matcher noParamCallMatcher = Patterns.noParamMethodCall.matcher(codeRow);

        Matcher ifWhileOpenerMatcher = Patterns.ifWhileOpener.matcher(codeRow);

        Matcher bracketCloserMatcher = Patterns.bracketCloser.matcher(codeRow);

        Matcher returnMatcher = Patterns.returnStatement.matcher(codeRow);

        if (commentMatcher.matches() || emptyLineMatcher.matches()){

        }
        else if (singleBooleanAssignmentMatcher.matches() || finalBooleanDeclarationMatcher.matches() ||
                singleIntAssignmentMatcher.matches() || finalIntDeclarationMatcher.matches() ||
                singleStringAssignmentMatcher.matches() || finalStringDeclarationMatcher.matches() ||
                singleDoubleAssignmentMatcher.matches() || finalDoubleDeclarationMatcher.matches() ||
                singleCharAssignmentMatcher.matches() || finalCharDeclarationMatcher.matches()){
            assignedVariableDeclaration(codeRow);
        }

        else if (charDeclarMatcher.matches() || stringDeclarMatcher.matches() || doubleDeclarMatcher
                .matches() || boolDeclarMatcher.matches() || intDeclarMatcher.matches())
            notAssignedVariableDeclaration(codeRow);

        else if (multiDoubelDeclarMatcher.matches() || multiCharDeclarMatcher.matches() ||
                multiStringDeclarMatcher.matches() || multiBoolDeclarMatcher.matches() ||
                multiIntDeclarMatcher.matches())
            multipleVariableDeclaration(codeRow);

        else if (boolAssignMatcher.matches() || intAssignMatcher.matches() || stringAssignMatcher.matches()
                || charAssignMatcher.matches() || doubleAssignMatcher.matches())
            variableAssignment(codeRow);

        else if (multiParamMethodMatcher.matches() || singleParamMethodMatcher.matches() ||
                noParamMethodMatcher.matches())
            methodDeclaration(codeRow);

        else if (noParamCallMatcher.matches() || singleParamCallMatcher.matches() ||  multiParamCallMatcher
                .matches())
            methodCall(codeRow);

        else if (ifWhileOpenerMatcher.matches())
            ifWhileBlock(codeRow);

        else if (bracketCloserMatcher.matches())
            closingBracket();

        else if (returnMatcher.matches()) {
            returnStatement();
            isLastLineReturn = true;
            return;
        }
        else
            throw new SyntaxException();

        isLastLineReturn = false; //updates the indicator (no return statement was found)
    }

    /**
     * @param string - a String
     * @param suffix - a char
     * @return a substring of the given string, without the given suffix if existed
     */
    private static String removeSuffix(String string, String suffix){
        if (string.contains(suffix))
            string = string.substring(0,string.indexOf(suffix));
        string = string.trim();
        return string;
    }

    /**
     * @param string - a String
     * @param prefix - a char
     * @return a substring of the given string, without the given prefix if existed
     */
    private static String removePrefix(String string, String prefix){
        if (string.contains(prefix))
            string = string.substring(string.indexOf(prefix)+1);
        return string;
    }

    /** this method receives a name of a variable and checks whether it's available for use or not (already
     *  used)
     * @param name - the name needs to be checked
     * @return true if the given name is a available for usage.
     * @throws NameInvalidException - if the given name is unavailable for usage.
     */
    private boolean isNameAvailable(String name)throws SyntaxException{
        //checks if the given variable name is defined in a method (local variable)
        if (programScope.getCurrentOpenedMethod() != null) {
            Scope currentScope;
            //if the given variable name is defined in a while/if block- set current scope as it
            if (!programScope.getCurrentOpenedMethod().getOpenedBlocks().empty())
                currentScope = programScope.getCurrentOpenedMethod().getOpenedBlocks().peek();
            else //if not- set current scope as the method
                currentScope = programScope.getCurrentOpenedMethod();
            //checks if the given variable name is already used in the current scope
            for (Variable variable : currentScope.getLocalVariables()) {
                if (variable.getName().equals(name)) //if the name is already used- throw exception
                    throw new SyntaxException();
            }
        }
        //else- the given variable name is defined an the main scope (global variable)
        else {
            //checks if the given variable name is already used in the main scope
            for (Variable variable: programScope.getGlobalVariables()){
                if (variable.getName().equals(name))
                    throw new SyntaxException();
            }
        }
        return true;
    }

    /**
     * this method receives a String of value, and returns it's type
     * @param value - String of a value
     * @return - the type of the given value
     */
    private String getValueType(String value){
        String valueType;
        Matcher doubleMatcher = Patterns.doublePattern.matcher(value);
        Matcher booleanMatcher = Patterns.booleanPattern.matcher(value);
        Matcher intMatcher = Patterns.intPattern.matcher(value);
        Matcher stringMatcher = Patterns.stringPattern.matcher(value);
        Matcher charMatcher = Patterns.charPattern.matcher(value);
        if (doubleMatcher.matches()) valueType = "double";
        else if (intMatcher.matches()) valueType = "int";
        else if (stringMatcher.matches()) valueType = "String";
        else if (booleanMatcher.matches()) valueType = "boolean";
        else if (charMatcher.matches()) valueType = "char";
        else valueType = "variable";
        return valueType;
        }

    /**
     * receives a variable name and searches for it from the most inner scope-out.
     * @param variableName - the variable needs to be found
     * @return the variable if found, null otherwise
     */
    private Variable getVariableByName(String variableName){
        //next 2 rows-if there is an opened method in the program - searches the variable in local variables
        Method currentMethod = programScope.getCurrentOpenedMethod();
        if (currentMethod != null) {
            //if there are opened if/while blocks in the method-searches the variable in their local variables
            if (!currentMethod.getOpenedBlocks().empty()){
                for (ConditionalBlock block : currentMethod.getOpenedBlocks()) {
                    for (Variable variable : block.getLocalVariables()) {
                        if (variable.getName().equals(variableName))
                            return variable;
                    }
                }
            }
            //searches the variable in method's local variables
            for (Variable variable : currentMethod.getLocalVariables()) {
                if (variable.getName().equals(variableName)) return variable;
            }
        }
        //searches the variable in global variables
        for (Variable variable : programScope.getGlobalVariables()) {
            if (variable.getName().equals(variableName)) return variable;
        }
        //if not found-return null
        return null;
    }

    /**
     *
     * @param type
     * @param valueType
     * @return
     */
    private boolean isValueTypeSuitable (String type, String valueType){
        if (valueType.equals(type)) return true;
        else if(type.equals("double") && valueType.equals("int")) return true;
        else if (type.equals("boolean") && (valueType.equals("int") || valueType.equals("double")))
            return true;
        else return false;
    }
    /** an assignment legality for varible declaration
     * @param type - the type of the assigned variable
     * @param value the value which was assigned into the variable
     * @return true iff the given assigned value suits the assigned variable type
     * @throws IncompetableAssignmentException -iff the the assigned value doesn't suit the variable type
     */
    private boolean isAssignmentLegal(String type, String value) throws SyntaxException,IncompetableAssignmentException{
        String valueType = getValueType(value); //holds the type of the assigned value
        if (isValueTypeSuitable(type, valueType)){
            return true;
        }
        // if the assigned value is a variable- search for it from local variable to global ones, and equals
        // it the the given value's type
        else if (valueType.equals("variable")) {
            Variable valueToAssign = getVariableByName(value);
            //if the given variable isn't declared & the assigned value is local- add the given variable to
            // variables to check in the end of compilation
            if (valueToAssign == null) {
                if (programScope.getCurrentOpenedMethod() != null) {
                    programScope.getGlobalVariablesToCheck().add(value);
                    return false;
                }
                else
                    throw new SyntaxException();
            }
            else { // else - the variable is found
                //check if it is assigned & throw exception if not
                if (!valueToAssign.isAssigned()) throw new SyntaxException();
                else {
                    if (valueToAssign.getType().equals(type))
                        return true;
                }
            }
        }
        else throw new IncompetableAssignmentException();
        return false;
    }

    /**
     * receives a code line of a variable declaration, and returns an array of Strings which holds the
     * variable type, name, and value.
     * @param codeRow - a String of a variable declaration code
     * @return a Strings array which holds:
     * variable type [index 0], variable name [index 1], assigned value[index 2]
     */
    private String[] getDeclarationDetails(String codeRow){
        codeRow = codeRow.trim();
        String[] splitCode = codeRow.split("\\s+");
        String variableType = splitCode[0];
        String trimmedCode = codeRow.substring(variableType.length()+1);
        splitCode = trimmedCode.split("=");
        String variableName = splitCode[0].trim();
        String assignedValue = splitCode[1];
        assignedValue = assignedValue.trim();
        assignedValue = removeSuffix(assignedValue,";");

        String[] detailsArray = new String[3];
        detailsArray[0] = variableType;
        detailsArray[1] = variableName;
        detailsArray[2] = assignedValue;

        return detailsArray;
    }


    // #### METHODS FOR PATTERNS #####

    /**
     * this method receives a code line of a variable declaration without assignment, and if the declaration
     * was legal, creates the variable accordingly
     * @param codeRow - a string of the variable declaration code
     * @throws NameInvalidException - iff the given name is unavailable for usage.
     */
    private void notAssignedVariableDeclaration(String codeRow)throws SyntaxException{
        codeRow = codeRow.trim();
        String[] splitCode = codeRow.split("\\s+");
        String variableType = splitCode[0];
        String variableName = splitCode[1];
        variableName = removeSuffix(variableName,";"); //removes the suffix ";" if exists

        if (isNameAvailable(variableName)) {
            Variable newVariable = new Variable(variableName, variableType, openedMethodIndicator,false,
                    false);
            Method currentMethod = programScope.getCurrentOpenedMethod();
            //check which scope is the current scope- and add the variable into it
            if (currentMethod != null){
                Scope currentScope;
                Stack<ConditionalBlock> openedBlocks = currentMethod.getOpenedBlocks();
                if (!openedBlocks.empty())
                    currentScope = openedBlocks.peek();
                else
                    currentScope = currentMethod;
                currentScope.getLocalVariables().add(newVariable);
            }
            else
                programScope.getGlobalVariables().add(newVariable);
        }

    }

    /**
     * this method receives a code line of a variable declaration with assignment, and if the declaration
     * was legal, creates the variable accordingly
     * @param codeRow - a string of the variable declaration code
     * @throws IncompetableAssignmentException - iff the the assigned value doesn't suit the variable type
     * @throws NameInvalidException - iff the given name is unavailable for usage.
     */
    private void assignedVariableDeclaration(String codeRow)throws IncompetableAssignmentException,
            SyntaxException, NameInvalidException, VariableNotDeclaredException {
        //next rows check whether the given declaration is 'final' or not- and sets an indicator
        codeRow = codeRow.trim();
        boolean finalIndicator = false;
        if (codeRow.startsWith("final")){
            finalIndicator = true;
            codeRow = codeRow.substring(FINAL_LENGTH);
        }
        //splits the type & name of declared variable, and the assigned value into strings
        String[] declarationDetails = getDeclarationDetails(codeRow);
        String variableType = declarationDetails[0];
        String variableName = declarationDetails[1];
        String assignedValue = declarationDetails[2];
        //if the declared variable name is available&the assigned value is suits the type- create the
        // variable
        if (isNameAvailable(variableName) && isAssignmentLegal(variableType, assignedValue)){
            Variable newVariable = new Variable(variableName, variableType, openedMethodIndicator, finalIndicator,
                    true);
            Method currentMethod = programScope.getCurrentOpenedMethod();
            //check which scope is the current scope- and add the variable into it
            if (currentMethod != null){
                Scope currentScope;
                Stack<ConditionalBlock> openedBlocks = currentMethod.getOpenedBlocks();
                if (!openedBlocks.empty())
                    currentScope = openedBlocks.peek();
                else
                    currentScope = currentMethod;
                currentScope.getLocalVariables().add(newVariable);
            }
            else
                programScope.getGlobalVariables().add(newVariable);
        }
    }

    /**
     * this method receives a code line of multiple variable declaration, and add the variables accordingly
     * if the declaration is legal, otherwise- throw exception
     * @param codeRow the given string of code line
     * @throws IncompetableAssignmentException
     * @throws NameInvalidException
     * @throws VariableNotDeclaredException
     */
    private void multipleVariableDeclaration(String codeRow)throws IncompetableAssignmentException,
            NameInvalidException, VariableNotDeclaredException, SyntaxException{
        codeRow = codeRow.trim();
        String variableType = codeRow.split("\\s+")[0]+" "; // gets the type of the variables
        codeRow = codeRow.substring(variableType.length()); // removes the type from the code line
        String[] splitCode = codeRow.split(",");
        for (String declaration: splitCode){
            declaration = variableType.concat(declaration);
            if (declaration.contains("="))
                assignedVariableDeclaration(declaration);
            else
                notAssignedVariableDeclaration(declaration);
        }
    }

    /**
     * this method is called when a closing bracket '}' was read in the code file. the method checks which
     * block needs to be closed, and:
     * @throws SyntaxException if there's no opened block to close, or if a method is closed without a
     * return statement in the previous line
     */
    private void closingBracket()throws SyntaxException{
        //if there's no opened method to close throw exception
        if (programScope.getCurrentOpenedMethod() == null) throw new SyntaxException();
        Stack<ConditionalBlock> methodOpenedBlocks = programScope.getCurrentOpenedMethod().getOpenedBlocks();
        if (!methodOpenedBlocks.empty()) //checks if there's an opened conditional block to close
            methodOpenedBlocks.pop();
        else if (isLastLineReturn) { //else - a method needs to be closed - check if the previous line was
            // return
            programScope.getClosedMethods().add(programScope.getCurrentOpenedMethod());
            programScope.setCurrentOpenedMethod(null);
            openedMethodIndicator = false;
        }
        else //method is closed without return statement in previous line
            throw new SyntaxException();
    }

    /**
     * turns on the previous return line indicator
     */
    private void returnStatement(){
        isLastLineReturn = true;
    }

    /**
     * this method gets a variable assignment code line as string, and checks whether the assignment was
     * legal or not
     * @param codeRow - the given assignment code line as string
     * @throws IncompetableAssignmentException - if the variable wasn't declared or the assigned value
     * doesn't match the variable type
     */
    private void variableAssignment(String codeRow)throws IncompetableAssignmentException, SyntaxException,
            VariableNotDeclaredException{
        //next rows splits the given string into variable name and assigned value
        codeRow = codeRow.trim();
        String[] splitCode = codeRow.split("=");
        String variableName = splitCode[0];
        variableName = variableName.trim();
        String assignedValue = splitCode[1];
        assignedValue = assignedValue.trim();
        assignedValue = removeSuffix(assignedValue, ";");
        //holds the type of the assigned value
        String assignedValueType = getValueType(assignedValue); // variableToAssign = assigned value
        Variable variableToAssign = getVariableByName(variableName);

        if(openedMethodIndicator){ //if current scope isn't a main scope
            if (variableToAssign != null){ //variable needs to be assigned is declared
                if(isValueTypeSuitable(variableToAssign.getType(),assignedValueType)){//if- the assigned value
                    // type matches the type of variable needs to be assigned
                    if (variableToAssign.isFinal()) throw new SyntaxException();
                    if (variableToAssign.isLocal())
                        variableToAssign.markAsAssigned();
                    return;
                }
                else if (assignedValueType.equals("variable")){ //else- if the assigned value is variable
                    Variable assignedVariable = getVariableByName(assignedValue);
                    //if the value assignment variable isn't declared- add it to variables to check
                    if (assignedVariable == null) {
                        programScope.getGlobalVariablesToCheck().add(assignedValue);
                        if (variableToAssign.isLocal())
                            variableToAssign.markAsAssigned();
                        return;
                    }
                    else{
                        //else- it's declared- check if types match

                        if (isValueTypeSuitable(variableToAssign.getType(),assignedVariable.getType())){
                            if (variableToAssign.isLocal())
                                variableToAssign.markAsAssigned();
                            return;
                        }
                        //else- the types don't match- throw exception
                        else throw new IncompetableAssignmentException();
                    }
                }
                else //the types don't match- throw exception
                    throw new IncompetableAssignmentException();
            }

            else{ // variable needs to be assigned not declared
                programScope.getGlobalVariablesToCheck().add(variableName);
                if (assignedValueType.equals("variable")){
                    Variable assignedVariable = getVariableByName(assignedValue);
                    //if the value assignment variable isn't declared- add it to variables to check
                    if (assignedVariable == null) {
                        programScope.getGlobalVariablesToCheck().add(assignedValue);
                        return;
                    }
                }
            }
        }
        else{ //else- current scope is the main scope
            if (variableToAssign == null) // if the variable needs to be assigned is undeclared
                    throw new VariableNotDeclaredException();
            else{ //else-it's declared
                //if the assigned value type suits the variable type
                if ( isValueTypeSuitable(variableToAssign.getType(),assignedValueType)){
                    variableToAssign.markAsAssigned();
                    return;
                }
                //else-if the assigned value is a variable
                else if (assignedValueType.equals("variable")) {
                    Variable assignedVariable = getVariableByName(assignedValue);
                    //if the value assignment variable isn't declared- throw exception
                    if (assignedVariable == null) throw new SyntaxException();
                    else{
                        //else- it's declared- check if types match
                        if (isValueTypeSuitable(variableToAssign.getType(),assignedVariable.getType())){
                            variableToAssign.markAsAssigned();
                            return;
                        }
                        //else- the types don't match- throw exception
                        else throw new IncompetableAssignmentException();
                    }
                }
            }
        }
    }

    /**
     * reads a method declaration code line, and creates it accordingly
     * @param codeRow - a String of the method declaration code line
     * @throws InvalidDeclarationException - if the method is defined in another method
     */
    private void methodDeclaration(String codeRow) throws InvalidDeclarationException, SyntaxException{
        //next rows removes the prefix "VOID" and splits the method's name and parameters declaration
        if(programScope.getCurrentOpenedMethod()!=null) throw new InvalidDeclarationException();
        codeRow = codeRow.trim();
        codeRow = codeRow.substring(VOID_LENGTH);
        codeRow = codeRow.trim();
        String methodName = codeRow.substring(0,codeRow.indexOf("("));
        String[] methodParameters = codeRow.substring(codeRow.indexOf("(")+1, codeRow.indexOf(")")).split(",");
        Method newMethod = new Method(methodName); //creates a new method and adds it to the main scope
        openedMethodIndicator = true;
        programScope.setCurrentOpenedMethod(newMethod);

        ArrayList<String> parametersTypes = new ArrayList<String>();
        //for each parameter- adds it's type to method's parameters types array, and creates a local variable
        for(String parameter: methodParameters) {
            parameter = parameter.trim();
            if (parameter.length() <= 1) continue;
            String variableType = parameter.substring(0, parameter.indexOf(" "));
            String variableName = parameter.substring(parameter.indexOf(" ") + 1).trim();
            for (Variable localVariable:newMethod.getLocalVariables()){
                if(localVariable.getName().equals(variableName)) throw new SyntaxException();
            }
            parametersTypes.add(variableType);
            Variable methodParameter = new Variable(variableName, variableType, true, false, true);
            newMethod.getLocalVariables().add(methodParameter);
        }
        newMethod.setParametersTypes(parametersTypes);
    }

    /**
     * this method receives a while\if block opener code line, and checks it legality.
     * @param codeRow - the code line of the while\if block opener
     * @throws IOError - if any of the boolean expressions is invalid
     */
    private void ifWhileBlock(String codeRow) throws IOError, SyntaxException{
        if(programScope.getCurrentOpenedMethod() == null) throw new InvalidUsageException();
        String expressionType;
        codeRow = codeRow.substring(codeRow.indexOf("(")+1, codeRow.indexOf(")"));
        String[] booleanExp = codeRow.split("(&&|\\|\\|)");
        //the next loop checks legality of each boolean expression
        for (String expression: booleanExp){
            expression = expression.trim();
            expressionType = getValueType(expression);
            //if the boolean expression is a String or char type- throw exception
            if (expressionType.equals("String") || expressionType.equals("char"))
                throw new SyntaxException();
            else if (expressionType.equals("variable")){
                Variable variableExpression = getVariableByName(expression);
                // if the given variable isn't declared or assigned- throw exception
                if (variableExpression == null || !variableExpression.isAssigned()) throw new SyntaxException();
                expressionType = variableExpression.getType();
                if (expressionType.equals("String") || expressionType.equals("char"))
                    throw new SyntaxException();
            }
        }
        ConditionalBlock newConditional = new ConditionalBlock();
        programScope.getCurrentOpenedMethod().getOpenedBlocks().add(newConditional);
    }


    /**
     * this method receives a method call code line and saves the details of it in an Arraylist, when the
     * first index is the method name, and the the following indexes are sent variable types by order
     * @param codeRow- the given method call code row
     * @throws SyntaxException if one of the sent parameters was an undefined variable
     */
    private void methodCall(String codeRow) throws SyntaxException {
        ArrayList<String> methodCalldetails = new ArrayList<String>();
        codeRow = codeRow.trim();
        //adds the name of the called method to the first index
        String methodName = codeRow.substring(0, codeRow.indexOf("(")).trim();
        methodCalldetails.add(methodName);

        String[] sentParameters = codeRow.substring(codeRow.indexOf("(")+1, codeRow.indexOf(")")).split(",");
        for (String parameter : sentParameters) {
            parameter = parameter.trim();
            if (parameter.length() < 1) continue;
            String paramType = getValueType(parameter);
            if (paramType.equals("variable")) {
                Variable paramVariable = getVariableByName(parameter);
                if (paramVariable == null)
                    throw new SyntaxException();
                else
                    methodCalldetails.add(paramVariable.getType());
            }
            else
                methodCalldetails.add(paramType);
        }
        programScope.getMethodCalls().add(methodCalldetails);
    }




    /**
     * receives a String of a method call code line, and checks whether the call is legal or not
     * @param methodDetails - an arraylist containing the method's details
     * @throws MethodNotDefinedException - if the called method isn't declared
     * @throws UnmatchedParametersException-if the given parameters doesn't match the actual method parameters
     */
    private void checkMethodCall(ArrayList<String> methodDetails)throws MethodNotDefinedException,
            UnmatchedParametersException, SyntaxException{
        String methodName = methodDetails.get(0);
        Method calledMethod = null; //will hold the method that has been called if ot was found
        //next loop checks if the called method exists
        for (Method optionalMethod: programScope.getClosedMethods()){
            if (methodName.equals(optionalMethod.getMethodName()))
                calledMethod = optionalMethod;
        }
        if (calledMethod == null) throw new MethodNotDefinedException();// exception- method not found

        if (methodDetails.size() == 1) return;
        //extracting the parameters that were given as arguments when the method was called.
        ArrayList<String> sentParameters = new ArrayList<String>();
        for (int i = 1; i < methodDetails.size(); i++){
            sentParameters.add(i -1,methodDetails.get(i));
        }
        ArrayList<String> actualParameters = calledMethod.getParametersTypes();
        //checks if the required number of parameters was sent
        if (sentParameters.size() != actualParameters.size()) throw new SyntaxException();
        for (int i=0; i < sentParameters.size(); i++){
            if (!isValueTypeSuitable(actualParameters.get(i), sentParameters.get(i))) throw new
                    UnmatchedParametersException();
        }
    }

    /**
     * this method checks legality of all method calls in the end of compilation
     * @throws MethodNotDefinedException - if one of the called methods isn't declared
     * @throws UnmatchedParametersException-if one of the method calls given parameters don't match the
     * actual method parameters
     */
    private void finalCheckMethodCalls()throws MethodNotDefinedException, UnmatchedParametersException, SyntaxException{
        for (ArrayList<String> methodCall: programScope.getMethodCalls())
            checkMethodCall(methodCall);
    }

    /**
     * this method check if a given name of a variable exists in global variables
     * @param variableToCheck the name of the given variable
     * @return true iff the given variable name was found
     */
    private boolean checkUsedVariable(String variableToCheck){
        for(Variable globalVariable: programScope.getGlobalVariables()){
            if (variableToCheck.equals(globalVariable.getName()) && globalVariable.isAssigned()) return true;
        }
        return false;
    }

    /**
     * this method checks if all used and undeclared variables inside method in the program were declared
     * later as assigned global variables
     * @throws VariableNotDeclaredException
     */
    private void finalCheckUsedVariables()throws SyntaxException{
        for (String usedVariable: programScope.getGlobalVariablesToCheck()){
            if (!checkUsedVariable(usedVariable)) throw new SyntaxException();
        }
    }

    /**
     * performs all necessary checks at the end of compilation
     * @throws IOError - if one of the method calls or used global variables aren't declared or used illegally
     */
    public void finishCompilation()throws IOError, SyntaxException{
        finalCheckMethodCalls();
        finalCheckUsedVariables();
    }

}
