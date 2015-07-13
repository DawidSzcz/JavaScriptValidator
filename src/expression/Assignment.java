package expression;

import java.util.HashMap;
import java.util.List;

import enums.Error;
public class Assignment extends Expression {
	 String first;
	 String second;
	 public Assignment(String name, int line, String a, String b)
	 {
		 super(name, line);
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
