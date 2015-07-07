package expression;

import java.util.LinkedList;
import java.util.List;

public abstract class Expression {
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
}
