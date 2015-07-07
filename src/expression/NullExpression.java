package expression;

public class NullExpression extends Expression{

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		return this;
	}

	@Override
	public String toString() {
		return "Null Expression";
	}

}
