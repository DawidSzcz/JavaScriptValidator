package expression;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;

import enums.Error;
import exception.WrongWhileException;

public class While extends ComplexExpression{
	String  condition;
	public While(String statement, int line, ExpressionParser expressionParser) throws WrongWhileException, IOException 
	{
		super(statement, line);
		Matcher arg = Patterns.arg.matcher(statement);
		Matcher states = Patterns.states.matcher(statement);
		String arguments, statements;
		if (arg.find())
			arguments = arg.group();
		else
			throw new WrongWhileException(Error.InvalidArguments, statement);
		if (states.find())
			statements = states.group();
		else {
			throw new WrongWhileException(Error.InvalidBlock, statement);
		}
		condition = arguments;
		this.statements = expressionParser.parse(statements);
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
		return "While";
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
