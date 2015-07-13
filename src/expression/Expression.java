package expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import enums.Error;

public abstract class Expression {
	protected String name;
	protected int line;
	List<enums.Error> errors = new LinkedList<>();
	public Expression(String name, int line) 
	{
		this.line = line;
		Matcher match = Patterns.line.matcher(name);
		if(match.find())
			this.name = match.group();
		else
			addError(enums.Error.UnparsedLine);
	}
	public abstract Expression get(int index) throws IndexOutOfBoundsException;

	public abstract String toString();
	public List<enums.Error> getErrors()
	{
		return errors;
	}
	public boolean hasErrors()
	{
		return false;
	}
	public void addError(enums.Error err)
	{
		errors.add(err);
	}
	public boolean match(String s)
	{
		Matcher match = Patterns.line.matcher(s);
		if(!match.find())
			return false;
		s = match.group();
		return name.contains(s);
	}
	public int getLine()
	{
		return line;
	}
	public HashMap<Integer, List<Error>> getAllErrors() {
		HashMap<Integer, List<Error>> hash = new HashMap<>();
		if(!errors.isEmpty())
			hash.put(line, getErrors());
		return hash;
	}
}
