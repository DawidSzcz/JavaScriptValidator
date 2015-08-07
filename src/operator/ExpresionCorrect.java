package operator;

import java.util.regex.Matcher;

import exception.InvalidFunction;
import exception.InvalidOperator;

public class ExpresionCorrect {

	public static boolean isExpressinCorrect(String expression) throws InvalidOperator,InvalidFunction {

		expression = functiontValidator(expression);
		expression = expression.replaceAll(Patterns.typeof, "variable");
		if(validator.Context.variableWithUnderscoreValid)
			underscoreValidator(expression); 
		expression = expression.replaceAll(Patterns.New, "variable");
		expression = expression.replaceAll(Patterns.Var, "variable");
		expression = expression.replaceAll(Patterns.variable, "variable");
		expression = expression.replaceAll(Patterns.number, "number");
		expression = squareBracketValidator(expression);
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
				String[] arguments = macherBracket.group().split(",");
	//			isFunctionExist(macherFunction.group(),arguments);
				for (String argument:arguments) {
						if (!isExpresionCorect(argument)) {
							throw new InvalidFunction(enums.Error.InvalidFunction, expression);
						}
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
			if (macherBracket.group().equals("")){
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
	private static void underscoreValidator(String expression) throws InvalidOperator{

		expression = expression.replaceAll("hideStructureAndParameters\\([^\\(\\)]+\\)", " ");
		expression = expression.replaceAll("checkParameter\\([^\\(\\)]+\\)", " ");
		expression = expression.replaceAll("hideTableAndParameters\\([^\\(\\)]+\\)", " ");
		expression = expression.replaceAll("hideTableParameters\\([^\\(\\)]+\\)", " ");

		Matcher matcherExpressionWithUnderscoreAndFunction = Patterns.expressionWithUnderscoreAndFunction.matcher(expression);
		
		while (matcherExpressionWithUnderscoreAndFunction.find()) {
			expression = expression.replace(matcherExpressionWithUnderscoreAndFunction.group(), "");
			matcherExpressionWithUnderscoreAndFunction = Patterns.expressionWithUnderscoreAndFunction.matcher(expression);
		}
	
		Matcher matcherExpressionWithUnderscore = Patterns.expressionWithUnderscore.matcher(expression);
		if (matcherExpressionWithUnderscore.find()){
			throw new InvalidOperator(enums.Error.IncorectExpresionWithUnderscore, expression);
		}
	}
	private static void isFunctionExist(String funktion, String[] arguments) throws InvalidFunction {
		String name = funktion.substring(0, funktion.indexOf('('));
		name = parser.ParseUtils.cleanLine(name);
		boolean isNumerArgumentCorect=false;
		if(validator.Context.functions.get(name)!=null){
			for(int arg:validator.Context.functions.get(name)){
				if(arg==arguments.length){
					isNumerArgumentCorect=true;
				}
			}
			if(!isNumerArgumentCorect){
				throw new InvalidFunction(enums.Error.IncorrectNumberOfArguments, funktion);
			}
		}else{
			throw new InvalidFunction(enums.Error.FunctionIsNotDeclared, funktion);
		}
		
	}
}