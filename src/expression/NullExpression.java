package expression;

import java.util.Map;

import Atoms.StringContainer;
import exception.UnknownException;

public class NullExpression extends Expression{

	public NullExpression(String name, int currentLine, Map<String, StringContainer> strings) {
		super(name, strings);
		// TODO Auto-generated constructor stub
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
		return "";
	}

	@Override
	public boolean isValid() 
	{
		return false;
	}

}
