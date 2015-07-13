package expression;

import exception.UnknownException;

public class NullExpression extends Expression{

	public NullExpression(String name, int line) {
		super(name, line);
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

}
