package operator;

import java.util.regex.Matcher;

public class OperatorCorrect {

	public static boolean isOpreratorCorrect(String expression) {

		expression = expression.replaceAll(Patterns.variable, "exp");
		Matcher macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);

		// s³abo rozwiazane!
		while (macherSquareBracket.find()) {

			if (!isExpresionCorect(macherSquareBracket.group())) 
				return false;
			else
				expression = expression.replace("[" + macherSquareBracket.group() + "]", "*exp");
			macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);
		}
		Matcher macherFunction = Patterns.function.matcher(expression);
		while (macherFunction.find()) {

			expression = expression.replace(macherFunction.group(), "exp");
			macherFunction = Patterns.function.matcher(expression);
		}
		Matcher macherBracket = Patterns.expressionInBracket.matcher(expression);
		while (macherBracket.find()) {
			if (!isExpresionCorect(macherBracket.group())) 
				return false;
			else
				expression = expression.replace("(" + macherBracket.group() + ")", "exp");
			macherBracket = Patterns.expressionInBracket.matcher(expression);
		}

		return isExpresionCorect(expression);
	}

	private static boolean isExpresionCorect(String expression) {
		Matcher matcherOperator1expression = Patterns.operator1expression.matcher(expression);

		while (matcherOperator1expression.find()) {
			expression = expression.replace(matcherOperator1expression.group(), "exp");
			matcherOperator1expression = Patterns.operator1expression.matcher(expression);
		}
		Matcher matcherOperator2expression = Patterns.operator2expressions.matcher(expression);
		while (matcherOperator2expression.find()) {
			expression = expression.replace(matcherOperator2expression.group(), "exp");
			matcherOperator2expression = Patterns.operator2expressions.matcher(expression);
		}

		expression = expression.replace(" ", "");
		if (expression.equals("exp"))
			return true;
		else
			return false;
	}
}
