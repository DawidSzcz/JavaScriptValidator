package expression;

import java.util.Map;

import Atoms.StringContainer;
import parser.ParseUtils;

public class InvalidExpression extends Expression{

	public InvalidExpression(String str, int currentLine, Map<String, StringContainer> strings) 
	{
		super(str, strings);
		line = currentLine + ParseUtils.getLinesBNS(str);
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
