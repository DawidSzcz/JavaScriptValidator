package expression;

import java.io.IOException;
import java.util.regex.Matcher;

import enums.Error;
import exception.WrongForException;

public class For extends ComplexExpression{
	Statement condition[];

	public For(String statement, int line, ExpressionParser expressionParser) throws IOException, WrongForException {
		super(statement, line);
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
		String[] conditions = arguments.split(";");
		if(conditions.length == 3)
		{
			this.condition[0]  = new Statement(conditions[0]);
			this.condition[1]  = new Statement(conditions[1]);
			this.condition[2]  = new Statement(conditions[2]);
		}
		else
			throw new WrongForException(Error.WrongNumberOfArguments, statement);
		this.statements = expressionParser.parse(statement);
		
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
		if(!super.isValid())
			return false;
		return(condition[0].isValid() && condition[1].isValid() && condition[2].isValid());
	}

}