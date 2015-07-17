package expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.Error;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongAssignmentException;
public class Assignment extends Expression {
	 Statement first;
	 Statement second;
	public Assignment(String statement, int currentLine, Map<String, String> strings) throws WrongAssignmentException {
		super(statement, currentLine, strings);
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
		try{
			first.isValid();
			second.isValid();
		}catch(InvalidOperator e)
		{
			this.addError(e.getError());
			return false;
		}catch(InvalidFunction e){
			this.addError(e.getError());
			return false;
		}
		return true;
	}
}
