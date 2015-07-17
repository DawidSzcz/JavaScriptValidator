package expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.Error;

public class Function extends ComplexExpression {
	public Function(String statement, int currentLine, Map<String, String> strings, ExpressionParser expressionParser) 
	{
		super(statement, currentLine, strings);
	}
	List<Expression> aguments;
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index -1);
	}
	@Override
	public String toString() {
		return "Function ";
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
