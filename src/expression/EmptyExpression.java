package expression;

import java.util.Map;

public class EmptyExpression extends Expression {

	public EmptyExpression() {
		super("Empty", 0, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Prosze stad wyjsc";
	}

	@Override
	public boolean isValid() {
		return false;
	}

}
