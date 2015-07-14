package expression;

import exception.InvalidOperator;
import operator.OperatorCorrect;

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
	public boolean isValid() throws InvalidOperator
	{
		return OperatorCorrect.isOpreratorCorrect(name);
	}
}
