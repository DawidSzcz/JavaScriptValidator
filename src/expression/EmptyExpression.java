package expression;

public class EmptyExpression extends Expression {

	public EmptyExpression() {
		super("Empty", null);
		// TODO Auto-generated constructor stub
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
