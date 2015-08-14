package parser;

import java.util.regex.Pattern;

import enums.Instruction;

public class Patterns {
	public static String CaseS = "(?<=\\W)case\\s+\\d+\\s*:";
	public static String DefaultS = "(?<=\\W)default\\s*:";
	public static String SwitchS = "^\\s*switch\\s*[\\(\\)]+";
	public static String ElseS = "^\\s*else";
	public static String splitS = "(;[\t \r]*)+|(?<=\\})|(?<=\\{)";
	public static String variableS = "\\a+\\w+";
	public static String argumentsS = "(?<=\\().+(?=\\)\\s*\\{)";
	public static String IfS = "^\\s*if\\s*[\\(\\)]+";
	public static String WhileS = "^\\s*([\\w$_]+:)?\\s*while\\s*[\\(\\)]+";
	public static String TryS = "^\\s*try";
	public static String CatchS = "^\\s*catch\\s*[\\(\\)]+";
	public static String FunctionS = "^\\s*function\\s+[\\w$_]+\\s*\\(";
	public static String blockS = "\\{[^\\}\\{]*\\}";
	public static String headerS = "(^\\s*|\\s+)(" 
									+ Instruction.IF 
									+"|" + Instruction.WHILE
									+"|" + Instruction.SWITCH
									+"|" + Instruction.FUNCTION
									+"|" + Instruction.CATCH
									+"|" + Instruction.FOR 
									+")|((" + Instruction.ELSE
									+"|" + Instruction.TRY
									+")(?=\\s*\\{))"; 
	public static String identiferS = "^\\s*BlockID-?\\d+";
	public static String headerIDS = "^\\s*(if|else|try|catch|switch|for|while|function)-?\\d+";
	public static String statementsS = "(?<=\\{).*(?=\\})";
	public static String conditionS = "(?<=\\().*(?=\\))";
	public static String singleStatement = "\\w+";
	public static String invocationS = "[^\\{\\}\\s]+";
	public static String checkOpenningS = "\\)(?![\t \r]*(\n|\\w))";
	public static String escapeWhiteSpaceS  = "[\\$_\\w\\(\\)\\{\\}]+(.*[\\w\\(\\)\\d\\[\\]_\\$\\+\"\':]+)*"; 	// Stare: [\\$_\\w\\(\\)\\d\\{\\}]+(.+[\\w\\(\\)\\d\\[\\]_\\$\\+]+)*
																												// dodany dwukropek. oby nic nie spieprzy³
	public static String lineS = "[\\{\\}\\w]+[^\n\r;]*";
	public static String ForS = "^\\s*([\\w$_]+:)?\\s*for\\s*[\\(]+";
	public static String stringIDS = "StringID\\d+"; 
	public static String commentS = "(\\/\\*([^*]|(\\*+[^*/]))*\\*+\\/)|(\\/\\/.*)";
	private static String commentLineS = ".*\\*\\/|(\\/\\*([^*]|(\\*+([^*/]|$)))*(\\*+\\/|$))|(\\/\\/.*)";
	private static String stringS = "\"[^\"\n\r]*\"|'[^'\n\r]*'";
	public static String assignDivisionS = "(?<![\\<\\>\\!\\=])(=|\\+=|-=|\\\\=|%=|\\*=)(?![\\<\\>\\!\\=])";
	public static String empty = "^\\s*$";
	public static String beginComplexS = "^\\s*%s";
	public static String stringsAndComentsS = "\\\"|\\'|\\/\\/|\\/\\*";
	public static String sqlExecuteStetmentFunctionS = "\\w+\\.executeStatement\\(\\s*[_$A-Za-z]\\w*\\s*\\)";
	public static String labelS = "(?<=(\n|^))\\s*[\\w_\\$]+:";
	public static String sqlGetPortFunctionS = "\\.getTytanDBPortFeature\\(\\)";
	public static String controlS = "(?<=(\n|^))\\s*break|continue";
	
	
	
	public static Pattern arg = Pattern.compile(argumentsS, Pattern.DOTALL);
	public static Pattern header = Pattern.compile(headerS, Pattern.CASE_INSENSITIVE);
	public static Pattern headerId = Pattern.compile(headerIDS);
	public static Pattern block = Pattern.compile(blockS);
	public static Pattern assign = Pattern.compile(assignDivisionS);
	public static Pattern If = Pattern.compile(Instruction.IF.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern While = Pattern.compile(Instruction.WHILE.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern function = Pattern.compile(Instruction.FUNCTION.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern Try = Pattern.compile(Instruction.TRY.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern Catch = Pattern.compile(Instruction.CATCH.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern For = Pattern.compile(Instruction.FOR.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern Else = Pattern.compile(Instruction.ELSE.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern Switch = Pattern.compile(Instruction.SWITCH.toString(), Pattern.CASE_INSENSITIVE);
	public static Pattern id = Pattern.compile(identiferS);
	public static Pattern states = Pattern.compile(statementsS, Pattern.DOTALL);
	public static Pattern condition = Pattern.compile(conditionS, Pattern.DOTALL);
	public static Pattern sinState = Pattern.compile(singleStatement);
	public static Pattern invocation = Pattern.compile(invocationS);
	public static Pattern checkOpenning = Pattern.compile(checkOpenningS);
	public static Pattern secondLine = Pattern.compile(lineS);
	public static Pattern stringID = Pattern.compile(stringIDS);
	public static Pattern comment = Pattern.compile(commentS);
	public static Pattern commentLine = Pattern.compile(commentLineS);
	public static Pattern string = Pattern.compile(stringS);
	public static Pattern escapeWhiteSpace =Pattern.compile(escapeWhiteSpaceS, Pattern.DOTALL);
	//public static Pattern elseIf = Pattern.compile(elseIfS);
	public static Pattern stringsAndComents = Pattern.compile(stringsAndComentsS);
	public static Pattern sqlExecuteStetmentFunction = Pattern.compile(sqlExecuteStetmentFunctionS);
	public static Pattern sqlGetPortFunction = Pattern.compile(sqlGetPortFunctionS);
	public static Pattern Case = Pattern.compile(CaseS);
	public static Pattern Default = Pattern.compile(DefaultS);
	public static Pattern label = Pattern.compile(labelS);
	public static Pattern control = Pattern.compile(controlS);
	public static Pattern Var = Pattern.compile(enums.Instruction.VAR.toString());
}
