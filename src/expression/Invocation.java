package expression;

public class Invocation extends Expression {
	String inv;
	public Invocation(String str) {
		inv = str;
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
		return "Invocation ";
	}
}
