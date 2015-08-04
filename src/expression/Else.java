package expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongComplexException;
import exception.WrongElseException;
import exception.WrongIfException;
import exception.WrongTryException;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.ParseUtils.Triple;
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