package expression;

public abstract class Expression {
	public abstract Expression get(int index) throws IndexOutOfBoundsException;

	public abstract String toString();
}
