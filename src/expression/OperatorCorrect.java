package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperatorCorrect {

	public static String isOpreratorCorrect(String expression) {
		int indeks = findOperator(expression);
		if (indeks != -1) {
			if (doubleOperator(expression, indeks).compareTo("increment") == 0
					|| doubleOperator(expression, indeks).compareTo("decrement") == 0) {
				String subExpression1 = expression.substring(0, indeks);
				String subExpression2 = expression.substring(indeks + 2);
				Pattern pattern = Pattern.compile("\\w+");
				Matcher matcher = pattern.matcher(subExpression1);
				Matcher matcher2 = pattern.matcher(subExpression2);
				if (matcher.find() && matcher2.find()) {
					return expression + " is incorrect";
				}
				if (!matcher.find() && !matcher2.find()) {
					return expression + " is incorrect";
				}
				if (matcher.find() && !matcher2.find()) {
					return isOpreratorCorrect(subExpression1);
				} else
					return isOpreratorCorrect(subExpression2);
			}
			if (doubleOperator(expression, indeks).compareTo("operation") == 0) {
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

	private static String doubleOperator(String expression, int indeks) {
		if (indeks != expression.length() - 1 && expression.charAt(indeks + 1) == '=') {
			if (expression.charAt(indeks) == '=' || expression.charAt(indeks) == '+' || expression.charAt(indeks) == '-'
					|| expression.charAt(indeks) == '*' || expression.charAt(indeks) == '/'
					|| expression.charAt(indeks) == '%') {
				return "operation";
			}
		}
		if (indeks != expression.length() - 1 && expression.charAt(indeks + 1) == '+'
				&& expression.charAt(indeks) == '+') {
			return "increment";
		}
		if (indeks != expression.length() - 1 && expression.charAt(indeks + 1) == '-'
				&& expression.charAt(indeks) == '-') {
			return "decrement";
		}

		return "false";
	}

	private static boolean isExpressionCorrect(String expression) {
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(expression);
		if (matcher.find()) {
			// if (matcher.group().compareTo(expression) == 0) {
			return true;
			// }
		}
		return false;
	}

	private static int findOperator(String expression) {

		for (int indeks = 0; indeks < expression.length(); indeks++) {
			if (indeks != expression.length() - 1 && expression.charAt(indeks + 1) == '+'
					&& expression.charAt(indeks) == '+'
					|| indeks != expression.length() - 1 && expression.charAt(indeks + 1) == '-'
							&& expression.charAt(indeks) == '-') {
				return indeks;
			}
		}
		for (int indeks = 0; indeks < expression.length(); indeks++) {
			if (expression.charAt(indeks) == '+' || expression.charAt(indeks) == '-' || expression.charAt(indeks) == '*'
					|| expression.charAt(indeks) == '/' || expression.charAt(indeks) == '=') {
				return indeks;
			}
		}

		return -1;
	}
}
