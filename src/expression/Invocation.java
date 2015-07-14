package expression;

import exception.InvalidOperator;

public class Invocation extends Expression {
	Statement invocation;
	public Invocation(String str, int line) {
		super(str, line);
		invocation = new Statement(ParseUtils.cleanLine(str));
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
		}
		return true;
	}
}
