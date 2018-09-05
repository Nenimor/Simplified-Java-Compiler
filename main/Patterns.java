package oop.ex6.main;

import java.util.regex.Pattern;

/**
 * This class organizes all the patterns in the program
 */
public class Patterns {

    //helpful regex expressions
    private static String nameRegex = "([a-zA-Z]\\w*|[^\\d]\\w+)";
    private static String intRegex = "(-?\\d+|"+nameRegex+")";
    private static String doubleRegex = "-?(\\d+|\\d+\\.\\d+|"+nameRegex+")";
    private static String stringRegex = "(\".*\"|"+nameRegex+")";
    private static String charRegex = "(\'.?\'|"+nameRegex+")";
    private static String booleanRegex = "(true|false|"+doubleRegex+"|"+nameRegex+")";
    private static String semicolonRegex = "\\s*;\\s*";
    private static String methodNameRegex = "([a-zA-Z]\\w*)";

    private static String variableDeclaration = "(final\\s+)?(String|int|double|boolean|char)\\s+"+nameRegex;


    // Pattern strings - Variables
    private static String singleIntDeclarationRegex = "\\s*int\\s+"+nameRegex+"\\s*;\\s*";
    private static String singleIntAssignmentRegex = "\\s*int\\s+"+nameRegex+"\\s*=\\s*"+intRegex+semicolonRegex;
    private static String finalIntDeclarationRegex = "\\s*final\\s+int\\s+"+nameRegex+"\\s*=\\s*"+intRegex+semicolonRegex;

    private static String singleDoubleDeclarationRegex = "\\s*double\\s+"+nameRegex+semicolonRegex;
    private static String singleDoubleAssignmentRegex = "\\s*double\\s+"+nameRegex+"\\s*=\\s*"+doubleRegex+semicolonRegex;
    private static String finalDoubleDeclarationRegex = "\\s*final\\s+double\\s+"+nameRegex+"\\s*=\\s*"+doubleRegex+semicolonRegex;

    private static String singleStringDeclarationRegex = "\\s*String\\s+"+nameRegex+semicolonRegex;
    private static String singleStringAssignmentRegex =
            "\\s*String\\s+"+nameRegex+"\\s*=[\\s*]"+stringRegex+semicolonRegex;

    private static String finalStringDeclarationRegex = "\\s*final\\s+String\\s+"+nameRegex+"\\s*=\\s*"+stringRegex+semicolonRegex;

    private static String singleCharDeclarationRegex = "\\s*char\\s+"+nameRegex+semicolonRegex;
    private static String singleCharAssignmentRegex = "\\s*char\\s+"+nameRegex+"\\s*=[\\s*]"+charRegex+semicolonRegex;
    private static String finalCharDeclarationRegex = "\\s*final\\s+char\\s+"+nameRegex+"\\s*=\\s*"+charRegex+semicolonRegex;

    private static String singleBooleanDeclarationRegex = "\\s*boolean\\s+"+nameRegex+semicolonRegex;
    private static String singleBooleanAssignmentRegex = "\\s*boolean\\s+"+nameRegex+"\\s*=\\s*"+booleanRegex+semicolonRegex;
    private static String finalBooleanDeclarationRegex = "\\s*final\\s+boolean\\s+"+nameRegex+"\\s*=\\s*"+booleanRegex+semicolonRegex;

    private static String intAssignmentRegex = nameRegex+"\\s*=\\s*"+intRegex+semicolonRegex;
    private static String stringAssignmentRegex = nameRegex+"\\s*=[\\s*]"+stringRegex+semicolonRegex;
    private static String doubleAssignmentRegex = nameRegex+"\\s*=\\s*"+doubleRegex+semicolonRegex;
    private static String charAssignmentRegex = nameRegex+"\\s*=[\\s*]"+charRegex+semicolonRegex;
    private static String booleanAssignmentRegex = nameRegex+"\\s*=\\s*"+stringRegex+semicolonRegex;


    // Pattren Strings - Methods, Ifs & Whiles
    private static String noParamMethodRegex = "\\s*void\\s+"+methodNameRegex+"\\s*[(]\\s*[)]\\s*[{]\\s*";
    private static String noParamMethodCallRegex = "\\s*"+methodNameRegex+"\\s*[(]\\s*[)]"+semicolonRegex;
    private static String singleParamMethodRegex = "\\s*void\\s+"+methodNameRegex+"\\s*[" +
            "(]\\s*"+variableDeclaration+"\\s*[)]\\s*[{]\\s*";
    private static String singleParamMethodCallRegex = "\\s*"+methodNameRegex+"\\s*[(]\\s*" +
            "("+intRegex+"|"+stringRegex+"|"+charRegex+"|"+booleanRegex+"|"+doubleRegex+")\\s*[)" +
            "]"+semicolonRegex;

    private static String multiParamMethodRegex = "\\s*void\\s+"+methodNameRegex+"\\s*[(]\\s*"+variableDeclaration+"" +
            "(\\s*,(\\s)*"+ variableDeclaration+")*\\s*[)]\\s*[{]\\s*";
    private static String multiParamMethodCallRegex = "\\s*"+methodNameRegex+"\\s*[(]\\s*" +
            "("+intRegex+"|"+stringRegex+"|"+charRegex+"|"+booleanRegex+"|"+doubleRegex+")(\\s*,(\\s)*" +
            "("+intRegex+"|"+stringRegex+"|"+charRegex+"|"+booleanRegex+"|"+doubleRegex+"))*\\s*[)]"+semicolonRegex;

    private static String ifWhileRegex = "\\s*(if|while)\\s*[(]\\s*"+booleanRegex+"(\\s(&&|\\|\\|)\\s*"+booleanRegex+")*\\s*[)"
            + "]\\s*[{]\\s*";
    private static String bracketCloseRegex = "\\s*[}]\\s*";
    private static String commentRegex = "^//.*";
    private static String multiIntVariableRegex = "\\s*int\\s+("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+intRegex+"\\s*)" +
            "(\\s*,(\\s)*("+nameRegex+"|"+nameRegex+"[\\s*]=[\\s*]"+intRegex+"))*+"+semicolonRegex;
    private static String multiStringVariableRegex = "\\s*int\\s+("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+stringRegex+")" +
            "(\\s*,(\\s)*("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+stringRegex+"))+"+semicolonRegex;
    private static String multiDoubleVariableRegex = "\\s*int\\s+("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+doubleRegex+")" +
            "(\\s*,(\\s)*("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+doubleRegex+"))+"+semicolonRegex;
    private static String multiCharVariableRegex = "\\s*int\\s+("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+charRegex+")" +
            "(\\s*,(\\s)*("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+charRegex+"))+"+semicolonRegex;
    private static String multiBooleanVariableRegex = "\\s*int\\s+("+nameRegex+"|"+nameRegex+"\\s*=\\s*"
            +booleanRegex+")(\\s*,(\\s)*("+nameRegex+"|"+nameRegex+"\\s*=\\s*"+booleanRegex+"))+"+semicolonRegex;
    private static String returnRegex = "\\s*return\\s*;\\s*";
    private static String emptyLineRegex = "\\s*";


    //Pattern Compilation
    public static Pattern singleIntDeclaration = Pattern.compile(singleIntDeclarationRegex);
    public static Pattern singleIntAssignment = Pattern.compile(singleIntAssignmentRegex);
    public static Pattern finalIntDeclaration = Pattern.compile(finalIntDeclarationRegex);

    public static Pattern singleDoubleDeclaration = Pattern.compile(singleDoubleDeclarationRegex);
    public static Pattern singleDoubleAssignment = Pattern.compile(singleDoubleAssignmentRegex);
    public static Pattern finalDoubleDeclaration = Pattern.compile(finalDoubleDeclarationRegex);

    public static Pattern singleStringDeclaration = Pattern.compile(singleStringDeclarationRegex);
    public static Pattern singleStringAssignment = Pattern.compile(singleStringAssignmentRegex);
    public static Pattern finalStringDeclaration = Pattern.compile(finalStringDeclarationRegex);

    public static Pattern singleCharDeclaration = Pattern.compile(singleCharDeclarationRegex);
    public static Pattern singleCharAssignment = Pattern.compile(singleCharAssignmentRegex);
    public static Pattern finalCharDeclaration = Pattern.compile(finalCharDeclarationRegex);

    public static Pattern singleBooleanDeclaration = Pattern.compile(singleBooleanDeclarationRegex);
    public static Pattern singleBooleanAssignment = Pattern.compile(singleBooleanAssignmentRegex);
    public static Pattern finalBooleanDeclaration = Pattern.compile(finalBooleanDeclarationRegex);

    public static Pattern intAssignment = Pattern.compile(intAssignmentRegex);
    public static Pattern stringAssignment = Pattern.compile(stringAssignmentRegex);
    public static Pattern doubleAssignment = Pattern.compile(doubleAssignmentRegex);
    public static Pattern charAssignment = Pattern.compile(charAssignmentRegex);
    public static Pattern booleanAssignment = Pattern.compile(booleanAssignmentRegex);

    public static Pattern noParamMethod = Pattern.compile(noParamMethodRegex);
    public static Pattern noParamMethodCall = Pattern.compile(noParamMethodCallRegex);
    public static Pattern singleParamMethodCall = Pattern.compile(singleParamMethodCallRegex);
    public static Pattern singleParamMethod = Pattern.compile(singleParamMethodRegex);
    public static Pattern multiParamMethod = Pattern.compile(multiParamMethodRegex);
    public static Pattern multiParamMethodCall = Pattern.compile(multiParamMethodCallRegex);

    public static Pattern ifWhileOpener = Pattern.compile(ifWhileRegex);
    public static Pattern bracketCloser = Pattern.compile(bracketCloseRegex);
    public static Pattern comment = Pattern.compile(commentRegex);
    public static Pattern multiIntVariable = Pattern.compile(multiIntVariableRegex);
    public static Pattern multiStringVariable = Pattern.compile(multiStringVariableRegex);
    public static Pattern multiDoubleVariable = Pattern.compile(multiDoubleVariableRegex);
    public static Pattern multiCharVariable = Pattern.compile(multiCharVariableRegex);
    public static Pattern multiBooleanVariable = Pattern.compile(multiBooleanVariableRegex);
    public static Pattern returnStatement = Pattern.compile(returnRegex);
    public static Pattern emptyLine = Pattern.compile(emptyLineRegex);
    public static Pattern methodName = Pattern.compile(methodNameRegex);
    public static Pattern doublePattern = Pattern.compile("-?\\d+\\.\\d+");
    public static Pattern booleanPattern = Pattern.compile("(true|false)");
    public static Pattern intPattern = booleanPattern.compile("-?\\d+");
    public static Pattern stringPattern = Pattern.compile("\".*\"");
    public static Pattern charPattern = Pattern.compile("\'.?\'");

}
