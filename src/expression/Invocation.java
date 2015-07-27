package expression;


import java.util.Map;

import exception.InvalidFunction;
import exception.InvalidOperator;
import parser.StringContainer;

public class Invocation extends Expression {
	Statement invocation;
	public Invocation(String str, int currentLine, Map<String, StringContainer> strings) {
		super(str, currentLine, strings);
		invocation = new Statement(str);
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
