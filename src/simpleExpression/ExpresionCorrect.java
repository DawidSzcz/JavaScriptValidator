package simpleExpression;

import java.util.regex.Matcher;

import exception.ExceptionContainer;
import exception.InvalidExpression;
import exception.InvalidString;
import parser.ParseUtils;

public class ExpresionCorrect {
	static int line = 0;
	static ExceptionContainer errors = new ExceptionContainer();
	public static boolean isExpressinCorrect(String expression) throws ExceptionContainer {
		errors = new ExceptionContainer();
		line = 0;
		functionValidator(expression);
		expression = restrictedWords(expression);
		if (validator.Context.variableWithUnderscoreValid)
			underscoreValidator(expression);

		expression = expression.replaceAll(Patterns.variable, "variable");
		expression = expression.replaceAll(Patterns.number, "number");
		expression = squareBracketValidator(expression);
		expression = FunctionsAndBrakets(expression);
		isExpresionCorect(expression);
		
		if(!errors.isEmpty()){
			throw errors;
		}else
			return true;
	}
	private static String FunctionsAndBrakets(String expression)
	{
		String exp = expression, Oldexp = expression;
		while(true)
		{
			exp = functiontAsExpressionValidator(exp);
			if(!exp.equals(Oldexp))
			{
				Oldexp = exp;
				continue;
			}
			exp = bracketValidator(exp);
			if(!exp.equals(Oldexp))
			{
				Oldexp = exp;
				continue;
			}
			break;
		}
		return exp;
	}
	private static String squareBracketValidator(String expression) {

		Matcher macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);
		while (macherSquareBracket.find()) {
			String subexpression = macherSquareBracket.group();
			subexpression = FunctionsAndBrakets(subexpression);
			if (!isExpresionCorect(subexpression)) {
				errors.addException(new InvalidExpression(enums.Error.InvalExpresionInSquareBracket, expression, line));
			} 
			expression = expression.replace("[" + macherSquareBracket.group() + "]", "");
			macherSquareBracket = Patterns.expressionInSquareBracket.matcher(expression);
		}
		return expression;
	}

	private static String functiontAsExpressionValidator(String expression) {
		Matcher macherFunction = Patterns.functionExpressions.matcher(expression);
		Matcher macherBracket;
		if(macherFunction.find()) {
			macherBracket = Patterns.expressionInBracket.matcher(macherFunction.group());
			if (macherBracket.find()) {
				String[] arguments = macherBracket.group().split(",");
				for (String argument : arguments) {
					
					try {
						if (!isExpressinCorrect(argument)) {
							errors.addException(new InvalidExpression(enums.Error.InvalidFunction, expression, line));
						}
					} catch (ExceptionContainer e) {
						   for(InvalidString error:e.getBeginningExceptions()){
							   errors.addException(error);
						   }
						   for(InvalidExpression error:e.getInlineExceptions()){
							   errors.addException(error);
						   }
						   errors.addException(new InvalidExpression(enums.Error.InvalidFunction, expression, line));
					}
				}
			}
			expression = expression.replace(macherFunction.group(), "variable");

		}
		return expression;
	}

	private static String bracketValidator(String expression){
		Matcher macherBracket;
		macherBracket = Patterns.expressionInBracket.matcher(expression);
		if(macherBracket.find()) {
			if (macherBracket.group().equals("")) {
				errors.addException(new InvalidExpression(enums.Error.NullInBracket, expression, line));
			}
			if (!isExpresionCorect(macherBracket.group()))
				errors.addException(new InvalidExpression(enums.Error.InvalExpresionInParenthesis, expression, line));
			expression = expression.replace("(" + macherBracket.group() + ")", "number");
		}
		return expression;
	}

	private static boolean isExpresionCorect(String expression){
		String enters="";
		expression = expression.replaceAll("^\\s+", "");
		expression = expression.replaceAll(Patterns.complexExpressions, "variable");	
		Matcher matcherThreePlus = Patterns.threePlus.matcher(expression);
		Matcher matcherThreeMinus = Patterns.threeMinus.matcher(expression);
		if (matcherThreePlus.find() || matcherThreeMinus.find()) {
			errors.addException( new InvalidExpression(enums.Error.IncorectExpresionWithUnderscore, expression, line));
			return false;
		}

		Matcher matcherOperator1expression = Patterns.operator1expression.matcher(expression);
		while (matcherOperator1expression.find()) {
			enters=ParseUtils.numberOfEnter(matcherOperator1expression.group());
			expression = ParseUtils.replaceFirst(matcherOperator1expression.group(), enters+"variable",expression);
			matcherOperator1expression = Patterns.operator1expression.matcher(expression);
		}
		Matcher matcherOperator2expression = Patterns.operator2expressions.matcher(expression);
		while (matcherOperator2expression.find()) {
			enters=ParseUtils.numberOfEnter(matcherOperator2expression.group());
			expression = ParseUtils.replaceFirst(matcherOperator2expression.group(), enters+"variable",expression);
			matcherOperator2expression = Patterns.operator2expressions.matcher(expression);
		}
		Matcher matcherQuestionMark = Patterns.questionMark.matcher(expression);
		while (matcherQuestionMark.find()) {
			enters=ParseUtils.numberOfEnter(matcherQuestionMark.group());
			expression = ParseUtils.replaceFirst(matcherQuestionMark.group(), enters+"variable",expression);
			matcherQuestionMark = Patterns.questionMark.matcher(expression);
		}
		for(int i=0; i < expression.length(); i++ ){
			if(expression.charAt(i)=='\n'){
				line++;
			}
			if(String.valueOf(expression.charAt(i)).matches("\\w")){
				break;
			}
		}
		expression = expression.replaceAll("\\s+", "");
		if (expression.equals("variable") || expression.equals("number") || expression.equals("")) {
			return true;
		} else if (!expression.replaceAll("variable|number", "").equals(""))
			errors.addException(new InvalidExpression(enums.Error.IncorrectMark, expression, line));
		else
			errors.addException(new InvalidExpression(enums.Error.SyntaxError, expression, line));
		return false;
	}

	private static void underscoreValidator(String expression){

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
			errors.addException(new InvalidExpression(enums.Error.IncorectExpresionWithUnderscore, expression, line));
		}
	}

	private static void functionValidator(String expression){
		expression = expression.replaceAll("_featureManager\\.getProcessInstanceFeature\\(\\)\\.getWFLIProcessId\\(\\)","");
		if (expression.indexOf("getWFLIProcessId()") != -1) {
			errors.addException(new InvalidExpression(enums.Error.InvalidUseGetWFLIProcessId, expression, line));
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
					boolean correctNumberOfArguments = false;
					for (int numberOfArguments : validator.Context.functions.get(oneOperatorFunction)) {
						if (functionArguments.length == numberOfArguments && !functionArguments[0].equals("")) {
							correctNumberOfArguments = true;
						}
						if (functionArguments.length == 1 && numberOfArguments == 0
								&& functionArguments[0].equals("")) {
							correctNumberOfArguments = true;
						}
					}
					if (!correctNumberOfArguments) {
						errors.addException(new InvalidExpression(enums.Error.WrongNumberOfArguments, expression, line));
					}
				}
			}
			for (String functionBehindDot : validator.Context.functionsBehindDot) {
				if (functionName.indexOf(functionBehindDot + "(") != -1) {
					if (functionName.indexOf('.' + functionBehindDot) == -1) {
						errors.addException(new InvalidExpression(enums.Error.MisssDotBeforFunctions, expression, line));
					}
				}
			}
		}
	}

	private static String restrictedWords(String expression){
		Matcher matcherForbiddenWords = Patterns.forbiddenWords.matcher(expression);
		if (matcherForbiddenWords.find()) {
			errors.addException(new InvalidExpression(enums.Error.UsedKeyWord, expression, line));
		}
		expression = expression.replaceAll(Patterns.prefiks, "variable");
		expression = expression.replaceAll(Patterns.Instanceof, "variable");
		return expression;
	}

	public static boolean declarationException(String expression) throws InvalidExpression{

		if (expression.matches("\\s*" + Patterns.variable + "\\s*")) {
			return true;
		}
		if (expression.matches("\\s*" + Patterns.exception + "\\s+" + Patterns.variable + "\\s*")) {
			return true;
		}

		throw new InvalidExpression(enums.Error.IncorrectDeclaredException, expression, line);
	}
}