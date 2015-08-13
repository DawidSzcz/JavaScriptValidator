package expression;

import java.util.Map;

import Atoms.StringContainer;
import parser.ParseUtils;

public class UnknownExpression extends Expression 
{
	public UnknownExpression(String str, int currentLine, Map<String, StringContainer> strings, String branch) {
		super(str, strings);
		this.branch = branch;
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

