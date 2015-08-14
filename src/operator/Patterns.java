package operator;

import java.util.regex.Pattern;

public class Patterns {

	public static String variable = "([_$A-Za-z][_$A-Za-z0-9]*|^\\s*-[_$A-Za-z]+[_$A-Za-z0-9]*|(?<=[^\\w\\)\\]-])\\s*-[_$A-Za-z]+[_$A-Za-z0-9]*)";
	public static String forbiddenWordsS = "(^|\\W)(try|else|if|switch|for|while|catch|function|var)(\\W|$)";
	public static String number = "[0-9]+|^\\s*-[0-9]+|(?<=[^\\w\\)\\]-])\\s*-[0-9]+";	
	public static String complexExpressions = "(variable\\.)+variable";
	public static String expressionInBracketS = "((?<=\\()[^\\)\\(]*(?=\\)))";
	public static String functionExpressionS = "(variable\\.)*variable\\s*(\\([^\\)\\(]*\\))";
	private static String functionS = "(("+variable+")\\.)*\\.?("+variable+")\\s*(\\([^\\)\\(]*\\))";
	public static String expressionInSquareBracketS = "(?<=\\w\\[)[^\\]\\[]*(?=\\])";
//	public static String splitFunctionArgumentsS = "^[^,]+(?=,)|(?<=,)[^,]+(?=,)|(?<=,)[^,]+$";
	public static String questionMarkS = "(number|variable)\\s*\\?\\s*(number|variable)\\s*:\\s*(number|variable)";
	private static String threePlusS = "\\+\\+\\+";
	private static String threeMinusS = "\\-\\-\\-";
	public static String expressionWithUnderscoreS = "\\W_[_$A-Za-z0-9]*";
	public static String expressionWithUnderscoreAndFunctionS = "\\W_[_$A-Za-z0-9]*(\\.[_$A-Za-z0-9]*)+\\s*\\(";
	public static String Instanceof = "("+variable+"\\.)*"+variable+"\\s+instanceof\\s+"+variable+"*(\\."+variable+")*";
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
	public static String prefiks =createprefix("new")
	+"|"+createprefix("void")
	+"|"+createprefix("typeof")
	+"|"+createprefix("import")
	+"|"+createprefix("float")
	+"|"+createprefix("char")
	+"|"+createprefix("byte")
	+"|"+createprefix("int")
	+"|"+createprefix("boolean");	
	public static Pattern expressionInBracket = Pattern.compile(expressionInBracketS);
	public static Pattern functionExpressions = Pattern.compile(functionExpressionS);
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
	public static Pattern forbiddenWords = Pattern.compile(forbiddenWordsS);
	
	private static String createRegex1(String operator) {
		return "(?<=\\W)\\s*"+operator+"\\s*(?=\\W)|^"+operator+"\\s*|\\s*"+operator+"$";
	}
	// "+operator+"
	private static String createRegex2(String operator) {
		return "\\s*(number|variable)\\s*"+operator+"\\s*(number|variable)\\s*";
	}
	private static String createprefix(String prefix) {
		return "((?<=\\W)|^)"+prefix+"\\s+[_$A-Za-z]\\w*";
	}
}
