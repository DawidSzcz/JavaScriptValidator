package expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.Error;

public class Program extends ComplexExpression 
{
	public Program(String name, int currentLine, List<Expression>stats, Map<String, String> strings) {
		super(name, currentLine, strings);
		statements = stats;
		line = 0;
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		return statements.get(index);
	}

	@Override
	public String toString() {
		return "Program";
	}
	public boolean isValid() {
		for(Expression exp : statements)
			if(!exp.isValid())
				return false;
		return true;
	}

}
