package expression;

public class Assignment extends Expression {
	 String first;
	 String second;
	 public Assignment(String name, String a, String b)
	 {
		 super(name);
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
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
