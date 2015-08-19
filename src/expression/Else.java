package expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongBlockException;
import exception.WrongComplexException;
import exception.WrongConditionException;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;

public class Else extends ComplexExpression
{
	private boolean elseIf = false;
	public Else(String statement, int currentLine, String branch)
	{
		super(statement, currentLine);
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.ELSE)).matcher(statement);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase);
		}
		this.branch = branch;
		area = 0;
	}
	public String toString()
	{
		return branch+"Else ";
	}

	public boolean isElseIf()
	{
		return elseIf;
	}
	@Override
	public boolean isValid() 
	{
		return super.isValid();
	}
	public boolean isEmpty() {
		return statements == null;
	}
	public void addIf(Expression exp) {
		if(statements == null)
			statements = new LinkedList<Expression>();
		statements.add(exp);
		elseIf = true;
		
	}
}