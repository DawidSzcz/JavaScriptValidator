package operator;

import java.util.regex.Pattern;

public class Patterns {
	public static String bracketS = "(\\([^\\)\\(]*\\))";
	public static String functionS = "\\w+(\\([^\\)\\(]*\\))";
	public static String squareBracketS = "(\\w+\\[[^\\]\\[]*\\])";

	public static Pattern bracket = Pattern.compile("bracketS");
	public static Pattern function = Pattern.compile(functionS);
	public static Pattern squareBracket = Pattern.compile(squareBracketS);
}
