package exception;

import java.util.LinkedList;
import java.util.List;


public class ExceptionContainer extends Exception {
	List<JSValidatorException> list = new LinkedList<>();
	public void addException(JSValidatorException exc)
	{
		list.add(exc);
	}
	public List<InvalidString> getBeginningExceptions()
	{
		List<InvalidString> l = new LinkedList<>();
		for(JSValidatorException ex : list)
			try{
				l.add((InvalidString)ex);
			}catch(Exception e)
		{	}
		return l;
	}
	public List<InvalidExpression> getInlineExceptions()
	{
		List<InvalidExpression> l = new LinkedList<>();
		for(JSValidatorException ex : list)
			try{
				l.add((InvalidExpression)ex);
			}catch(Exception e)
		{	}
		return l;
	}
	public boolean isEmpty()
	{
		return list.isEmpty();
	}
}
