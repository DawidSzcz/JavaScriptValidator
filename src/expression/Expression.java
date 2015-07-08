package expression;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import exception.UnknownException;

public abstract class Expression {
	protected String name;
	public Expression(String name) 
	{
		Matcher match = Patterns.line.matcher(name);
		if(match.find())
			this.name = match.group();
		else
			addError("unparsed name");
	}
	public abstract Expression get(int index) throws IndexOutOfBoundsException;

	public abstract String toString();
	List<String> errors = new LinkedList<>();
	public List<String> getErrors()
	{
		return errors;
	}
	public boolean hasErrors()
	{
		return false;
	}
	public void addError(String err)
	{
		errors.add(err);
	}
	public String getName()
	{
		return name;
	}
}
