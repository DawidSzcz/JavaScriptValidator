package expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import enums.Error;
import exception.UnknownException;

public class InvalidBlock extends ComplexExpression {

	List<String> states = new LinkedList<>();
	
	public InvalidBlock(String block, int line) throws UnknownException
	{
		super(block, line);
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

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		return statements.get(index);
	}

}
