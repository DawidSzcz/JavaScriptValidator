package expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import enums.Error;
import exception.UnknownException;

public class InvalidExpression extends Expression{

	public InvalidExpression(String str, int line) 
	{
		super(str, line);
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
		return name;
	}
	public boolean hasErrors()
	{
		return true;
	}
	@Override
	public boolean isValid() 
	{
		return false;
	}

}
