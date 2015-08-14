package Atoms;

import exception.InvalidExpression;
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
	public void setName(String name){
		this.name = name;
		return;
	}
	public boolean isValid() throws InvalidExpression
	{
		return ExpresionCorrect.isExpressinCorrect(name);
	}
}
