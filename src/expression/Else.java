package expression;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;

import enums.Error;

public class Else extends If
{
	List<Expression> elseStatements;
	String elseName;
	int elseLine;
	Else(String name, int line, If If, List<Expression> els)
	{
		super(If.getName(), If.line, If.getCondition(), If.getStatements());
		elseLine = line;
		Matcher match =Patterns.line.matcher(name);
		match.find();
		elseName = match.group();
		elseStatements = els;
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
}