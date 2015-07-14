package expression;

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
	public boolean isValid()
	{
		return OperatorCorrect.isOpreratorCorrect(name);
	}
}
