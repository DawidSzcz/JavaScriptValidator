package operator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.InvalidFunction;
import exception.InvalidOperator;

public class ExpresionCorrect {

	public static boolean isExpressinCorrect(String expression) throws InvalidOperator, InvalidFunction {

		functionValidator(expression);
		expression = restrictedWords(expression);
		if (validator.Context.variableWithUnderscoreValid)
			underscoreValidator(expression);
		expression = expression.replaceAll(Patterns.variable, "variable");
		expression = expression.replaceAll(Patterns.number, "number");
		expression = squareBracketValidator(expression);
		expression = functiontAsExpressionValidator(expression);
		expression = bracketValidator(expression);
		if (!isExpresionCorect(expression)) {
			throw new InvalidOperator(enums.Error.InvalidOperator, expression);
		}
		return true;
	}

	private static String squareBracketValidator(String expression) throws InvalidOperator, InvalidFunction {

		Matcher macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);
		while (macherSquareBracket.find()) {
			String subexpression = macherSquareBracket.group();
			subexpression = functiontAsExpressionValidator(subexpression);
			subexpression = bracketValidator(subexpression);
			if (!isExpresionCorect(subexpression)) {
				throw new InvalidOperator(enums.Error.InvalExpresionInSquareBracket, expression);
			} else
				expression = expression.replace("[" + macherSquareBracket.group() + "]", "");
			macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);
		}
		return expression;
	}

	private static String functiontAsExpressionValidator(String expression) throws InvalidOperator, InvalidFunction {
		Matcher macherFunction = Patterns.functionExpressions.matcher(expression);
		Matcher macherBracket;
		while (macherFunction.find()) {
			macherBracket = Patterns.expressionInBracket.matcher(macherFunction.group());
			if (macherBracket.find()) {
				String[] arguments = macherBracket.group().split(",");
				for (String argument : arguments) {
					if (!isExpresionCorect(argument)) {
						throw new InvalidFunction(enums.Error.InvalidFunction, expression);
					}
				}
			}
			expression = expression.replace(macherFunction.group(), "variable");
			macherFunction = Patterns.functionExpressions.matcher(expression);
		}
		return expression;
	}

	private static String bracketValidator(String expression) throws InvalidOperator {
		Matcher macherBracket;
		macherBracket = Patterns.expressionInBracket.matcher(expression);
		while (macherBracket.find()) {
			if (macherBracket.group().equals("")) {
				throw new InvalidOperator(enums.Error.NullInBracket, expression);
			}
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
		if (matcherThreePlus.find() || matcherThreeMinus.find()) {
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

	private static void underscoreValidator(String expression) throws InvalidOperator {

		expression = expression.replaceAll("hideStructureAndParameters\\([^\\(\\)]+\\)", " ");
		expression = expression.replaceAll("checkParameter\\([^\\(\\)]+\\)", " ");
		expression = expression.replaceAll("hideTableAndParameters\\([^\\(\\)]+\\)", " ");
		expression = expression.replaceAll("hideTableParameters\\([^\\(\\)]+\\)", " ");

		Matcher matcherExpressionWithUnderscoreAndFunction = Patterns.expressionWithUnderscoreAndFunction
				.matcher(expression);

		while (matcherExpressionWithUnderscoreAndFunction.find()) {
			expression = expression.replace(matcherExpressionWithUnderscoreAndFunction.group(), "");
			matcherExpressionWithUnderscoreAndFunction = Patterns.expressionWithUnderscoreAndFunction
					.matcher(expression);
		}

		Matcher matcherExpressionWithUnderscore = Patterns.expressionWithUnderscore.matcher(expression);
		if (matcherExpressionWithUnderscore.find()) {
			throw new InvalidOperator(enums.Error.IncorectExpresionWithUnderscore, expression);
		}
	}

	private static void functionValidator(String expression) throws InvalidOperator {
		expression = expression.replaceAll("_featureManager\\.getProcessInstanceFeature\\(\\)\\.getWFLIProcessId\\(\\)","");
		if (expression.indexOf("getWFLIProcessId()") != -1) {
			throw new InvalidOperator(enums.Error.InvalidUseGetWFLIProcessId, expression);
		}
		String functionName;
		String[] functionArguments;
		Matcher macherFunction = Patterns.function.matcher(expression);
		Matcher macherBracket;
		while (macherFunction.find()) {
			functionName = macherFunction.group().substring(0, macherFunction.group().indexOf('(') + 1);
			macherBracket = Patterns.expressionInBracket.matcher(macherFunction.group());
			macherBracket.find();
			functionArguments = macherBracket.group().split(",");
			for (String oneOperatorFunction : validator.Context.functions.keySet()) {
				if (functionName.indexOf(oneOperatorFunction + "(") != -1) {
					boolean correctNumberOfArguments=false;
					for (int numberOfArguments : validator.Context.functions.get(oneOperatorFunction)) {
						if (functionArguments.length == numberOfArguments && !functionArguments[0].equals("")) {
							correctNumberOfArguments=true;
						}
						if (functionArguments.length==1 && numberOfArguments==0 && functionArguments[0].equals("")){
							correctNumberOfArguments=true;
						}
					}
					if(!correctNumberOfArguments){
						throw new InvalidOperator(enums.Error.IncorrectNumberOfArguments, expression);
					}
				}
			}
			for (String functionBehindDot : validator.Context.functionsBehindDot) {
				if (functionName.indexOf(functionBehindDot + "(") != -1) {
					if (functionName.indexOf('.' + functionBehindDot) == -1) {
						throw new InvalidOperator(enums.Error.MisssDotBeforFunctions, expression);
					}
				}
			}
		}
	}
	private static String restrictedWords(String expression) throws InvalidOperator {
		Matcher matcherForbiddenWords= Patterns.forbiddenWords.matcher(expression);
		if(matcherForbiddenWords.find()){
			throw new InvalidOperator(enums.Error.UsedKeyWord, expression);
		}
		expression = expression.replaceAll(Patterns.prefiks, "variable");
		expression = expression.replaceAll(Patterns.Instanceof, "variable");
		return expression;
	}

}