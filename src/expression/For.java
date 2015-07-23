package expression;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import enums.Error;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongAssignmentException;
import exception.WrongForException;
import parser.ExpressionParser;
import parser.Patterns;

public class For extends ComplexExpression{
	Expression[] forConditions = new Expression[3];

	public For(String statement, int currentLine, Map<String, String> strings, ExpressionParser expressionParser) throws IOException, WrongForException {
		super(statement, currentLine, strings);
		Matcher arg = Patterns.arg.matcher(statement);
		Matcher states = Patterns.states.matcher(statement);
		String arguments,statements; 
		if (arg.find())
			arguments = arg.group();
		else
			throw new WrongForException(Error.InvalidArguments, statement);
		if (states.find())
			statements = states.group();
		else {
			throw new WrongForException(Error.InvalidBlock, statement);
		}
		String[] conditions = (arguments+" ").split(";");
		if(conditions.length == 3)
		{
			for(int i = 0; i < 3; i++)
				if(!conditions[i].matches(Patterns.empty))
				{
					List<Expression> list = expressionParser.parseExpressions(conditions[i]);
					if(list.size() != 1)
						throw new WrongForException(Error.InvalidForCondition, statement);
					else
						forConditions[i] = list.get(0);
				}
				else
					forConditions[i] = new EmptyExpression();
					
					
		}
		else
			throw new WrongForException(Error.WrongNumberOfArguments, statement);
		this.statements = expressionParser.parseExpressions(statements);
		
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index - 1);
	}

	@Override
	public String toString() {
		return "For";
	}

	@Override
	public boolean isValid() {
		//Wykomentowane bo wszyskie elementy condition i tak sa walidowane w parserze
//		boolean isValid = super.isValid();
//		for(Expression e : forConditions)
//			if(!e.isValid())
//				isValid = false;
//		return isValid;
		return true;
	}
	@Override
	public boolean hasErrors() {
		for(Expression e : forConditions)
			if(e.hasErrors())
				return true;
		return false;
	}
	@Override
	public List<Error> getErrors() {
		List<Error> errs= super.getErrors();
		for(Expression e : forConditions)
			errs.addAll(e.getErrors());
		return errs;
	}

}