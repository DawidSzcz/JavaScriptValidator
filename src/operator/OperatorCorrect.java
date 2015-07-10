package operator;

import java.util.regex.Matcher;

public class OperatorCorrect {

	public static boolean isOpreratorCorrect(String expression) {

		Matcher macherBracket = Patterns.bracket.matcher(expression);
		Matcher macherFunction = Patterns.function.matcher(expression);
		Matcher macherSquareBracket = Patterns.squareBracket.matcher(expression);

		while (macherSquareBracket.find()) {
			if (!isExpresionCorect(macherSquareBracket.group())) {
				return false;
			} else
				expression = expression.replace(macherSquareBracket.group(), "x");
		}
		
		while (macherFunction.find()) {
			if (!isFunction(macherFunction.group())) {
				return false;
			} else
				expression = expression.replace(macherFunction.group(), "x");
		}
		
		while (macherBracket.find()) {
			if (!isExpresionCorect(macherBracket.group())) {
				return false;
			} else
				expression = expression.replace(macherBracket.group(), "x");
		}

		return isExpresionCorect(expression);
	}

	private static boolean isFunction(String group) {
		// TODO Auto-generated method stub
		return true;
	}

	private static boolean isExpresionCorect(String group) {
		// TODO Auto-generated method stub
		return true;
	}
}
