package expression;

public class Invocation extends Expression {
	public Invocation(String str, int line) {
		super(str, line);
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
}
