package expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class InvalidBlock extends Expression {

	String error;
	List<String> states = new LinkedList<>();
	
	public InvalidBlock(String block)
	{
		error = block;
		states = Arrays.asList(block.split("\\{|\\;"));
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if (index < states.size())
			return new InvalidExpression(states.get(index));
		else
			throw new IndexOutOfBoundsException();
	}

	@Override
	public String toString() {
		return error;
	}
	public boolean hasErrors()
	{
		return true;
	}
	public List<String> getErrors()
	{
		LinkedList<String> list =  new LinkedList<String>();
		list.add(error);
		return list;
	}

}
