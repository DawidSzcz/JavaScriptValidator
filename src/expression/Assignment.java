package expression;

public class Assignment extends Expression {
	 String first;
	 String second;
	 public Assignment(String a, String b)
	 {
		 first = a;
		 second = b;
	 }
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			throw new IndexOutOfBoundsException();
	}
	public String toString()
	{
		return "Assignment";
	}
}
