package expression;

import java.util.Map;

import Atoms.StringContainer;

public class NullExpression extends Expression{

	public NullExpression(String name, int currentLine, Map<String, StringContainer> strings) {
		super(name, strings);
		// TODO Auto-generated constructor stub
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
