package expression;

import java.util.HashMap;
import java.util.List;

import enums.Error;
import exception.WrongAssignmentException;
public class Assignment extends Expression {
	 Statement first;
	 Statement second;
	 public Assignment(String name, int line, String a, String b)
	 {
		 super(name, line);
		 first = new Statement(a);
		 second = new Statement(b);
	 }
	public Assignment(String statement, int line) throws WrongAssignmentException {
		super(statement, line);
		statement = ParseUtils.cleanLine(statement);
		String side[] = statement.split("=");
		if(side.length == 2)
		{
			first = new Statement(side[0]);
			second = new Statement(side[1]);
		}
		else
			throw new WrongAssignmentException(Error.WrongAssignment, statement);
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
		return first.isValid() && second.isValid();
	}
}
