package operator;

import java.util.regex.Pattern;

public class Patterns {

	public static String variable = "[_$A-Za-z][_$A-Za-z0-9]*|^\\s*-[_$A-Za-z]+[_$A-Za-z0-9]*|(?<=[^\\w\\)\\]-])\\s*-[_$A-Za-z]+[_$A-Za-z0-9]*";
	public static String number = "[0-9]+|^\\s*-[0-9]+|(?<=[^\\w\\)\\]-])\\s*-[0-9]+";
	public static String New = "((?<=\\W)|^)new\\s+\\w+";
	public static String Var = "((?<=\\W)|^)var\\s+[_$A-Za-z]\\w*";
	public static String typeof = "((?<=\\W)|^)typeof\\s+\\w+";
	public static String complexExpressions = "(variable\\.)+variable";
	public static String expressionInBracketS = "((?<=\\()[^\\)\\(]*(?=\\)))";
	public static String functionS = "(variable\\.)*variable\\s*(\\([^\\)\\(]*\\))";
	public static String expressionInSquareBracketS = "(?<=\\w\\[)[^\\]\\[]*(?=\\])";
//	public static String splitFunctionArgumentsS = "^[^,]+(?=,)|(?<=,)[^,]+(?=,)|(?<=,)[^,]+$";
	public static String questionMarkS = "(number|variable)\\s*\\?\\s*(number|variable)\\s*:\\s*(number|variable)";
	private static String threePlusS = "\\+\\+\\+";
	private static String threeMinusS = "\\-\\-\\-";
	public static String expressionWithUnderscoreS = "\\W_[_$A-Za-z0-9]*";
	public static String expressionWithUnderscoreAndFunctionS = "\\W_[_$A-Za-z0-9]*(\\.[_$A-Za-z0-9]*)+\\s*\\(";
	public static String operator1expressionS = createRegex1("\\+\\+variable")
	+ "|" + createRegex1("variable\\+\\+")
	+ "|" + createRegex1("variable\\-\\-")
	+ "|" + createRegex1("\\-\\-variable")
	+ "|" + createRegex1("\\~(number|variable)")
	+ "|" + createRegex1("\\!(number|variable)");
	public static String operator2expressionsS = createRegex2("\\+")
	+ "|" + createRegex2("\\-")
	+ "|" + createRegex2("\\*")
	+ "|" + createRegex2("\\/")
	+ "|" + createRegex2("\\%")
	+ "|" + createRegex2("\\=\\=")
	+ "|" + createRegex2("\\!\\=")
	+ "|" + createRegex2("\\=\\=\\=")
	+ "|" + createRegex2("\\!\\=\\=")
	+ "|" + createRegex2("\\>")
	+ "|" + createRegex2("\\<")
	+ "|" + createRegex2("\\>\\=")
	+ "|" + createRegex2("\\<\\=")
	+ "|" + createRegex2("\\>\\>")
	+ "|" + createRegex2("\\<\\<")
	+ "|" + createRegex2("\\|\\|")
	+ "|" + createRegex2("\\&\\&");
	
	public static Pattern expressionInBracket = Pattern.compile(expressionInBracketS);
	public static Pattern function = Pattern.compile(functionS);
	public static Pattern expressionInSquareBracket = Pattern.compile(expressionInSquareBracketS);
	public static Pattern operator1expression = Pattern.compile(operator1expressionS);
	public static Pattern operator2expressions = Pattern.compile(operator2expressionsS);
//	public static Pattern splitFunctionArguments = Pattern.compile(splitFunctionArgumentsS);
	public static Pattern questionMark = Pattern.compile(questionMarkS);
	public static Pattern threePlus = Pattern.compile(threePlusS);
	public static Pattern threeMinus = Pattern.compile(threeMinusS);
	public static Pattern expressionWithUnderscore = Pattern.compile(expressionWithUnderscoreS);
	public static Pattern expressionWithUnderscoreAndFunction = Pattern.compile(expressionWithUnderscoreAndFunctionS);
	
	private static String createRegex1(String operator) {
		return "(?<=\\W)\\s*"+operator+"\\s*(?=\\W)|^"+operator+"\\s*|\\s*"+operator+"$";
	}
	// "+operator+"
	private static String createRegex2(String operator) {
		return "\\s*(number|variable)\\s*"+operator+"\\s*(number|variable)\\s*";
	}
}
