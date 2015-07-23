package expression;

import java.util.regex.Pattern;

public class Patterns {
	public static String ElseS = "^\\s*else";
	public static String splitS = ";+\\s*\n"; // Dopisane /n trzeba sie teraz bedzie gimnastykowac z podwojnymi instrukcjami; 
	public static String variableS = "\\a+\\w+";
	public static String argumentsS = "(?<=\\().+(?=\\)\\s*\\{)";
	public static String headerS = "[^\\{]+";
	public static String complexS = "(if)|(while)|(function)|(for)";
	public static String IfS = "^\\s*if";
	public static String WhileS = "^\\s*while";
	public static String FunctionS = "^\\s*function";
	public static String blockS = "(^|\\s+)(((if|while|function)[^;\\{]+|for[^\\{]+)|else)[\\s]*\\{[^\\}\\{]+\\}"; // Dodany nie-œrednik !!! Dodany osobny przypadek dla fora z srednikiem
	public static String identiferS = "^\\s*BlockID-?\\d+";
	public static String statementsS = "(?<=\\{).+(?=\\})";
	public static String singleStatement = "\\w+";
	public static String invocationS = "[^\\{\\}\\s]+";
	public static String checkOpenningS = "\\)\\s*\n+\\s*\\w";
	public static String escapeWhiteSpaceS  = "[\\$_\\w\\(\\)\\{\\}]+(.*[\\w\\(\\)\\d\\[\\]_\\$\\+]+)*"; // Stare: [\\$_\\w\\(\\)\\d\\{\\}]+(.+[\\w\\(\\)\\d\\[\\]_\\$\\+]+)*
	public static String lineS = "[\\{\\}\\w]+[^\n\r;]*";
	public static String ForS = "^\\s*for";
	public static String stringIDS = "StringID\\d+"; 
	public static String commentS = "(\\/\\*([^*]|(\\*+[^*/]))*\\*+\\/)|(\\/\\/.*)";
	private static String commentLineS = ".*\\*\\/|(\\/\\*([^*]|(\\*+([^*/]|$)))*(\\*+\\/|$))|(\\/\\/.*)";
	private static String stringS = "\"[^\"\n\r]*\"|'[^'\n\r]*'";
	public static String assignDivisionS = "(?<![\\<\\>\\!\\=])(=|\\+=|-=|\\\\=|%=|\\*)(?![\\<\\>\\!\\=])";
	public static String elseIfS = "(?<=else)\\s*BlockID-?\\d+\\s*$";
	public static String empty = "^\\*s$";

	
	public static Pattern arg = Pattern.compile(argumentsS, Pattern.DOTALL);
	public static Pattern head = Pattern.compile(headerS);
	public static Pattern complex = Pattern.compile(complexS);
	public static Pattern block = Pattern.compile(blockS);
	public static Pattern assign = Pattern.compile(assignDivisionS);
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
	public static Pattern Else = Pattern.compile(ElseS);
	public static Pattern stringID = Pattern.compile(stringIDS);
	public static Pattern comment = Pattern.compile(commentS);
	public static Pattern commentLine = Pattern.compile(commentLineS);
	public static Pattern string = Pattern.compile(stringS);
	public static Pattern escapeWhiteSpace =Pattern.compile(escapeWhiteSpaceS, Pattern.DOTALL);
	public static Pattern elseIf = Pattern.compile(elseIfS);

}
