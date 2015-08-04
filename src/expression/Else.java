package expression;

import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Instruction;
import exception.WrongComplexException;
import parser.ExpressionParser;
import parser.Patterns;

public class Else extends ComplexExpression
{
	private boolean elseIf= false;
	public Else(String statement, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser)
	{
		super(statement, Instruction.ELSE, currentLine,strings);
		Matcher matcherElseIf = Patterns.elseIf.matcher(statement);
		if(matcherElseIf.find()){
			elseIf = true; 
			statement=statement.replaceFirst("else", "");
			try{
				super.splitBlock(Instruction.IF, currentLine, statement);
			}catch(WrongComplexException e){
				this.addError(e.getError());
			}
		}
		if(content != null)
			statements = expressionParser.parseExpressions(content, beginOfStatements);
	}
	public String toString()
	{
		return "Else";
	}
	@Override
	public Expression get(int i) throws IndexOutOfBoundsException
	{
		if(i == 0)
			return this;
		else 
			return statements.get(i);
	}
	public boolean isElseIf()
	{
		return elseIf;
	}

	@Override
	public boolean isValid() 
	{
		return true;
	}
}