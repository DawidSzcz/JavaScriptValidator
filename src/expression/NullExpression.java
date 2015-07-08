package expression;

import exception.UnknownException;

public class NullExpression extends Expression{

	public NullExpression(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		return this;
	}

	@Override
	public String toString() {
		return "Null Expression";
	}

}
