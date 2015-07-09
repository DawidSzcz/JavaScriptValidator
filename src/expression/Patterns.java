package expression;

import java.util.regex.Pattern;

public class Patterns {
	public static String splitS = ";+\\s*";
	public static String variableS = "\\a+\\w+";
	public static String cleanS = "\\w.+";
	public static String argumentsS = "(?<=\\().+(?=\\))";
	public static String headerS = "[^\\{]+";
	public static String complexS = "(if)|(while)|(function)|(for)";
	public static String IfS = "^\\s*if";
	public static String WhileS = "^\\s*while";
	public static String FunctionS = "^\\s*function";
	public static String blockS = "((if)|(while)|(function)|(for))[^\\n]+[\\s]*(\\{[^\\}\\{]+\\})";
	public static String assignS = "\\s*\\w+ *=";
	public static String identiferS = "^-?\\d+";
	public static String statementsS = "(?<=\\{).+(?=\\})";
	public static String singleStatement = "\\w+";
	public static String invocationS = "(^[^=]+(\\.|\\;))+";
	public static String checkOpenningS = "\\)\\s*\\n+\\s*\\w";
	public static String lineS = "[\\{\\}\\w]+[^\\n\r]*";
	public static String ForS = "^\\s*for";

	public static Pattern clean = Pattern.compile(cleanS);
	public static Pattern arg = Pattern.compile(argumentsS);
	public static Pattern head = Pattern.compile(headerS);
	public static Pattern complex = Pattern.compile(complexS);
	public static Pattern block = Pattern.compile(blockS);
	public static Pattern assign = Pattern.compile(assignS);
	public static Pattern If = Pattern.compile(IfS);
	public static Pattern While = Pattern.compile(WhileS);
	public static Pattern function = Pattern.compile(FunctionS);
	public static Pattern id = Pattern.compile(identiferS);
	public static Pattern states = Pattern.compile(statementsS, Pattern.DOTALL);
	public static Pattern sinState = Pattern.compile(singleStatement);
	public static Pattern invocation = Pattern.compile(invocationS);
	public static Pattern checkOpenning = Pattern.compile(checkOpenningS);
	public static Pattern line = Pattern.compile(lineS);
	public static Pattern For = Pattern.compile(ForS);

}
