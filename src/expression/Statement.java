package expression;

import exception.InvalidFunction;
import exception.InvalidOperator;
import operator.ExpresionCorrect;

public class Statement {
	private String name;
	public Statement(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public boolean isValid() throws InvalidOperator, InvalidFunction
	{
		return ExpresionCorrect.isExpressinCorrect(name);
	}
}
