package expression;


import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import exception.InvalidFunction;
import exception.InvalidOperator;
import parser.ParseUtils;

public class Invocation extends Expression {
	Statement invocation;
	public Invocation(String str, int currentLine, Map<String, StringContainer> strings) {
		super(str, strings);
		invocation = new Statement(str);
		line = currentLine + ParseUtils.getLinesBNS(str);
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
		return "Invocation";
	}
	@Override
	public boolean isValid() {
		try{
			invocation.isValid();
		}catch(InvalidOperator e)
		{
			this.addError(e.getError());
			return false;
		}catch(InvalidFunction e){
			this.addError(e.getError());
			return false;
		}
		return true;
	}
}
