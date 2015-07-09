package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Ostatnio dodane: "~" "<<" ">>" "===" "!==" ">>>" "!liczba"
public class OperatorCorrect {

	public static String isOpreratorCorrect(String expression) {
		expression = operatorsWithOneVariable(expression);
		int indeks = findOperator(expression);
		if (indeks != -1) {
			if (tripleOperator(expression, indeks)){
				String subExpression1 = expression.substring(0, indeks);
				String subExpression2 = expression.substring(indeks + 3);
				return isOpreratorCorrect(subExpression1) + isOpreratorCorrect(subExpression2);
			}
			else if (doubleOperator(expression, indeks)) {
				String subExpression1 = expression.substring(0, indeks);
				String subExpression2 = expression.substring(indeks + 2);
				return isOpreratorCorrect(subExpression1) + isOpreratorCorrect(subExpression2);
			} else {
				String subExpression1 = expression.substring(0, indeks);
				String subExpression2 = expression.substring(indeks + 1);
				return isOpreratorCorrect(subExpression1) + isOpreratorCorrect(subExpression2);
			}

		} else

		{
			if (isExpressionCorrect(expression)) {
				return "";
			} else {
				return expression + " is incorrect";
			}
		}

	}

	private static String operatorsWithOneVariable(String expression) {
		
		String regex=createRegexToOWOV("\\+\\+");
		regex+="|"+createRegexToOWOV("\\-\\-");
		regex+="|"+createRegexToOWOV("\\>\\>");
		regex+="|"+createRegexToOWOV("\\<\\<");
		regex+="|"+createRegexToOWOV("\\~");
		regex+="|"+createRegexToOWOV("\\!\\w");
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(expression);
		while (matcher.find()) {
			expression = expression.replace(matcher.group(), "x");
			matcher = pattern.matcher(expression);
		}

		return expression;
	}
	private static String createRegexToOWOV(String operator){
		return "\\w+"+operator+"(?=\\W)|(?<=\\W)"+operator+"\\w+|^"+operator+"\\w+|\\w+"+operator+"$";
	}
	private static boolean doubleOperator(String expression, int indeks) {
		if (indeks != expression.length() - 1 && expression.charAt(indeks + 1) == '=') {
			if (expression.charAt(indeks) == '=' || expression.charAt(indeks) == '+' || expression.charAt(indeks) == '-'
					|| expression.charAt(indeks) == '*' || expression.charAt(indeks) == '/'
					|| expression.charAt(indeks) == '%' || expression.charAt(indeks) == '!'
					|| expression.charAt(indeks) == '<' || expression.charAt(indeks) == '>') {
				return true;
			}
		}
		return false;
	}

	private static boolean tripleOperator(String expression, int indeks) {
		if (indeks != expression.length() - 2 && expression.charAt(indeks + 1) == '=' && expression.charAt(indeks + 2) == '=') {
			if (expression.charAt(indeks) == '=' | expression.charAt(indeks) == '!') {
				return true;
			}
		}
		if (indeks != expression.length() - 2 && expression.charAt(indeks) == '>'&& expression.charAt(indeks + 1) == '>' && expression.charAt(indeks + 2) == '>'){
			return true;
		}
		return false;
	}

	private static boolean isExpressionCorrect(String expression) {
		Pattern pattern = Pattern.compile("\\s*\\w+\\s*");
		Matcher matcher = pattern.matcher(expression);
		if (matcher.find()) {
			if (matcher.group().compareTo(expression) == 0) {
				return true;
			}
		}
		return false;
	}

	private static int findOperator(String expression) {

		for (int indeks = 0; indeks < expression.length(); indeks++) {
			if (expression.charAt(indeks) == '+' || expression.charAt(indeks) == '-' || expression.charAt(indeks) == '*'
					|| expression.charAt(indeks) == '/' || expression.charAt(indeks) == '='
					|| expression.charAt(indeks) == '%' || expression.charAt(indeks) == '<'
					|| expression.charAt(indeks) == '>' || expression.charAt(indeks) == '&'
					|| expression.charAt(indeks) == '|' || expression.charAt(indeks) == '^') {
				return indeks;
			}
		}

		return -1;
	}
}
