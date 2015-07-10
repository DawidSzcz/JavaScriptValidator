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
				expression = expression.replace(macherSquareBracket.group(), "exe");
			macherSquareBracket = Patterns.squareBracket.matcher(expression);
		}
		
		while (macherFunction.find()) {
			if (!isFunction(macherFunction.group())) {
				return false;
			} else
				expression = expression.replace(macherFunction.group(), "exe");
			macherFunction = Patterns.function.matcher(expression);
		}
		
		while (macherBracket.find()) {
			if (!isExpresionCorect(macherBracket.group())) {
				return false;
			} else
				expression = expression.replace(macherBracket.group(), "exe");
			macherBracket = Patterns.bracket.matcher(expression);
		}

		return isExpresionCorect(expression);
	}

	private static boolean isFunction(String expression) {
		
		return true;
	}

	private static boolean isExpresionCorect(String expression) {
		Matcher matcherOperator1expression=Patterns.operator1expression.matcher(expression);
		Matcher matcherOperator2expression=Patterns.operator2expressions.matcher(expression);
		
		while (matcherOperator1expression.find()) {
				expression = expression.replace(matcherOperator1expression.group(), "exe");
				matcherOperator1expression=Patterns.operator1expression.matcher(expression);
		}
		while (matcherOperator2expression.find()) {
			expression = expression.replace(matcherOperator2expression.group(), "exe");
			matcherOperator2expression=Patterns.operator2expressions.matcher(expression);
		}
		if (expression.equals("exe")){
		 	return true;
		}
		else
			return false;
	}
}
