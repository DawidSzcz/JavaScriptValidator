package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Do zrobienia "~" "<<" ">>" "===" "!==" ">>>" "!liczba"
public class OperatorCorrect {

	public static String isOpreratorCorrect(String expression) {
		expression = incDecValidator(expression);
		int indeks = findOperator(expression);
		if (indeks != -1) {
			if (doubleOperator(expression, indeks)) {
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

	private static String incDecValidator(String expression) {
		String regex = "\\w+\\+\\+(?=\\W)|(?<=\\W)\\+\\+\\w+|\\w+\\-\\-(?=\\W)|(?<=\\W)\\-\\-\\w+|^\\-\\-\\w+|^\\+\\+\\w+|\\w+\\+\\+$|\\w+\\-\\-$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(expression);
		while (matcher.find()) {
			expression = expression.replace(matcher.group(), "x");
			matcher = pattern.matcher(expression);
		}

		return expression;
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
