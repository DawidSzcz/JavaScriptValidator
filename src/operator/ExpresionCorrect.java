package operator;

import java.util.regex.Matcher;

import exception.InvalidFunction;
import exception.InvalidOperator;

public class ExpresionCorrect {

	public static boolean isExpressinCorrect(String expression) throws InvalidOperator,InvalidFunction {

		expression = expression.replaceAll(Patterns.New, "variable");
		expression = expression.replaceAll(Patterns.Var, "variable");
		expression = expression.replaceAll(Patterns.typeof, "variable");
		expression = expression.replaceAll(Patterns.variable, "variable");
		expression = expression.replaceAll(Patterns.number, "number");
		expression = squareBracketValidator(expression);
		expression = functiontValidator(expression);
		expression = bracketValidator(expression);
		if (!isExpresionCorect(expression)) {
			throw new InvalidOperator(enums.Error.InvalidOperator, expression);
		}
		return true;
	}

	private static String squareBracketValidator(String expression) throws InvalidOperator, InvalidFunction {

		Matcher macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);
		while (macherSquareBracket.find()) {
			String subexpression=macherSquareBracket.group();
			subexpression = functiontValidator(subexpression);
			subexpression = bracketValidator(subexpression);			
			if (!isExpresionCorect(subexpression)) {
				throw new InvalidOperator(enums.Error.InvalExpresionInSquareBracket, expression);
			} else
				expression = expression.replace("[" + macherSquareBracket.group() + "]", "*variable");
			macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);
		}
		return expression;
	}

	private static String functiontValidator(String expression) throws InvalidOperator,InvalidFunction {
		Matcher macherFunction = Patterns.function.matcher(expression);
		Matcher macherBracket;
		while (macherFunction.find()) {
			macherBracket = Patterns.expressionInBracket.matcher(macherFunction.group());
			if (macherBracket.find()) {
				String argunets = macherBracket.group();
				Matcher matcherArgument = Patterns.splitFunctionArguments.matcher(argunets);
				if (matcherArgument.find()) {
					do {
						if (!isExpresionCorect(matcherArgument.group())) {
							throw new InvalidFunction(enums.Error.InvalidFunction, expression);
						} else {
							argunets = argunets.replace(matcherArgument.group() + ",", "");
							matcherArgument = Patterns.splitFunctionArguments.matcher(argunets);
						}
					} while (matcherArgument.find());
				}
				if (!isExpresionCorect(argunets) && !macherBracket.group().equals("")) {
					throw new InvalidOperator(enums.Error.InvalidOperator, expression);
				}
			}
			expression = expression.replace(macherFunction.group(), "variable");
			macherFunction = Patterns.function.matcher(expression);
		}
		return expression;
	}

	private static String bracketValidator(String expression) throws InvalidOperator {
		Matcher macherBracket;
		macherBracket = Patterns.expressionInBracket.matcher(expression);
		while (macherBracket.find()) {
			if (!isExpresionCorect(macherBracket.group()))
				throw new InvalidOperator(enums.Error.InvalExpresionInParenthesis, expression);
			else
				expression = expression.replace("(" + macherBracket.group() + ")", "number");
			macherBracket = Patterns.expressionInBracket.matcher(expression);
		}
		return expression;
	}

	private static boolean isExpresionCorect(String expression) throws InvalidOperator {

		expression = expression.replaceAll(Patterns.complexExpressions, "variable");
		
		Matcher matcherThreePlus = Patterns.threePlus.matcher(expression);
		Matcher matcherThreeMinus = Patterns.threeMinus.matcher(expression);
		if (matcherThreePlus.find() || matcherThreeMinus.find() ){
			return false;
		}
		
		Matcher matcherOperator1expression = Patterns.operator1expression.matcher(expression);
		while (matcherOperator1expression.find()) {
			expression = expression.replace(matcherOperator1expression.group(), "variable");
			matcherOperator1expression = Patterns.operator1expression.matcher(expression);
		}
		Matcher matcherOperator2expression = Patterns.operator2expressions.matcher(expression);
		while (matcherOperator2expression.find()) {
			expression = expression.replace(matcherOperator2expression.group(), "variable");
			matcherOperator2expression = Patterns.operator2expressions.matcher(expression);
		}
		Matcher matcherQuestionMark = Patterns.questionMark.matcher(expression);
		while (matcherQuestionMark.find()) {
			expression = expression.replace(matcherQuestionMark.group(), "variable");
			matcherQuestionMark = Patterns.operator2expressions.matcher(expression);
		}
		expression = expression.replaceAll("\\s+", "");
		if (expression.equals("variable") || expression.equals("number") || expression.equals(""))
			return true;
		else
			return false;
	}

}
