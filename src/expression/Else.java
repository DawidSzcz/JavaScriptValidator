package expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import enums.Error;
import exception.WrongElseException;
import exception.WrongIfException;

public class Else extends If
{
	List<Expression> elseStatements;
	String elseName;
	int elseLine;
	public Else(String statement, int line, If If, ExpressionParser expressionParser) throws WrongIfException, IOException 
	{
		super((If).getName(), (If).line, (If).getCondition(), (If).getStatements());
		Matcher states = Patterns.states.matcher(statement);
		String statements;
		if (states.find())
			statements = states.group();
		else {
			throw new WrongIfException(Error.InvalidBlock, statement);
		}
		elseLine = line;
		elseName = ParseUtils.cleanLine(name);
		elseStatements = expressionParser.parse(statements);
	}
	public String toString()
	{
		return "ElseIF";
	}
	@Override
	public Expression get(int i) throws IndexOutOfBoundsException
	{
		if(i <= this.statements.size())
			return super.get(i);
		else if(i == this.statements.size() + 1)
				return new NullExpression(elseName, elseLine);
			else
				return elseStatements.get(i - statements.size() - 2);
	}
	@Override
	public boolean match(String s) {
		Matcher match = Patterns.line.matcher(s);
		match.find();
		s = match.group();
		return (super.match(s)|| elseName.contains(s));
	}
	@Override
	public HashMap<Integer, List<Error>> getAllErrors() {
		HashMap<Integer, List<Error>> hash = super.getAllErrors();
		for(Expression exp : elseStatements)
		{
			for(Integer line: exp.getAllErrors().keySet())
			{
				if(!hash.keySet().contains(line))
					hash.put(line, exp.getAllErrors().get(line));
				else
					hash.get(line).addAll(exp.getAllErrors().get(line));
			}
		}
		return hash;
	}
	@Override
	public boolean isValid() {
		if(!super.isValid())
			return false;
		for(Expression exp: statements)
			if(!exp.isValid())
				return false;
		return true;
	}
}