package expression;

import java.util.LinkedList;
import java.util.List;

public class InvalidExpression extends Expression{

	String statement;
	public InvalidExpression(String str) {
		statement = str;
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			throw new IndexOutOfBoundsException();
	}

	@Override
	public String toString() {
		return statement;
	}
	public boolean hasErrors()
	{
		return true;
	}
	public List<String> getErrors()
	{
		return errors;
	}

}
