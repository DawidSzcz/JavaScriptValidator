package expression;

import java.util.HashMap;
import java.util.List;

import enums.Error;

public class Program extends ComplexExpression 
{
	public Program(String name, List<Expression>stats) {
		super(name, 0);
		statements = stats;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		return statements.get(index);
	}

	@Override
	public String toString() {
		return "Program";
	}

}
