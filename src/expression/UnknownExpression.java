package expression;

import java.util.LinkedList;
import java.util.List;

public class UnknownExpression extends Expression 
{
	public UnknownExpression(String str) {
		super(str);
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		throw new IndexOutOfBoundsException();
	}

	@Override
	public String toString() {
		return name;
	}
	public boolean hasErrors()
	{
		return true;
	}
	public List<String> getErrors()
	{
		return errors;
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}


}
