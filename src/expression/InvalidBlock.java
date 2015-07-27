package expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import enums.Error;
import exception.UnknownException;
import parser.StringContainer;

public class InvalidBlock extends ComplexExpression {

	List<String> states = new LinkedList<>();
	
	public InvalidBlock(String block, int currentLine, Map<String, StringContainer> strings) throws UnknownException
	{
		super(block, currentLine, strings);
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
