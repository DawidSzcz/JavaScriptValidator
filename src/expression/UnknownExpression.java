package expression;

public class UnknownExpression extends Expression {

	String statement;
	public UnknownExpression(String str) {
		statement = str;
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public String toString() {
		return statement;
	}

}
