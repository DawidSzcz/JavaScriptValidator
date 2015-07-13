package operator;

import java.util.regex.Pattern;

public class Patterns {
	public static String bracketS = "(\\([^\\)\\(]*\\))";
	public static String functionS = "\\w+(\\([^\\)\\(]*\\))";
	public static String squareBracketS = "(\\w+\\[[^\\]\\[]*\\])";
	public static String operator1expressionS = createRegex1("\\+\\+\\w+")
	+"|" + createRegex1("\\w+\\+\\+")
	+"|" + createRegex1("\\w+\\-\\-")
	+"|" + createRegex1("\\-\\-\\w+")
	+"|" + createRegex1("\\~\\w+")
	+"|" + createRegex1("\\!\\w+");
	public static String operator2expressionsS = createRegex2("\\+")
	+"|"+createRegex2("\\-")
	+"|"+createRegex2("\\*")
	+"|"+createRegex2("\\/")
	+"|"+createRegex2("\\%")
	+"|"+createRegex2("\\=\\=")
	+"|"+createRegex2("\\!\\=")
	+"|"+createRegex2("\\=\\=\\=")
	+"|"+createRegex2("\\!\\=\\=")
	+"|"+createRegex2("\\>")
	+"|"+createRegex2("\\<")
	+"|"+createRegex2("\\>\\=")
	+"|"+createRegex2("\\<\\=")
	+"|" + createRegex2("\\>\\>")
	+"|" + createRegex2("\\<\\<");
	
	public static Pattern bracket = Pattern.compile("bracketS");
	public static Pattern function = Pattern.compile(functionS);
	public static Pattern squareBracket = Pattern.compile(squareBracketS);
	public static Pattern operator1expression = Pattern.compile(operator1expressionS);
	public static Pattern operator2expressions = Pattern.compile(operator2expressionsS);
	
	private static String createRegex1(String operator) {
		return "(?<=\\W)\\s*"+operator+"\\s*(?=\\W)|^"+operator+"\\s*|\\s*"+operator+"$";
	}
	// "+operator+"
	private static String createRegex2(String operator) {
		return "\\s*\\w+\\s*"+operator+"\\s*\\w+\\s*";
	}
}
