package expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Atoms.StringContainer;
import enums.Error;
import parser.ParseUtils;

public class UnknownExpression extends Expression 
{
	public UnknownExpression(String str, int currentLine, Map<String, StringContainer> strings) {
		super(str, strings);
		line = currentLine + ParseUtils.getLinesBNS(str);
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
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

