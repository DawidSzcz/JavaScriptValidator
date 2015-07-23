package expression;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import enums.Error;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongWhileException;
import parser.ExpressionParser;
import parser.Patterns;

public class While extends ComplexExpression{
	Statement  condition;
	public While(String statement, int currentLine, Map<String, String> strings, ExpressionParser expressionParser) throws WrongWhileException, IOException 
	{
		super(statement, currentLine, strings);
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
		condition = new Statement(arguments);
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
		return "While";
	}
	@Override
	public boolean isValid() {
		if(!super.isValid())
			return false;
		try{
			return condition.isValid();
		}catch(InvalidOperator e)
		{
			this.addError(e.getError());
			return false;
		}catch(InvalidFunction e){
			this.addError(e.getError());
			return false;
		}
	}
}
