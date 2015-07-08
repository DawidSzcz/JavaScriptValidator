package expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import exception.UnknownException;

public class InvalidBlock extends Expression {

	List<String> states = new LinkedList<>();
	
	public InvalidBlock(String block) throws UnknownException
	{
		super(block);
		states = Arrays.asList(block.split("\\{|\\;"));
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
		LinkedList<String> list =  new LinkedList<String>();
		list.add(name);
		return list;
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

}
