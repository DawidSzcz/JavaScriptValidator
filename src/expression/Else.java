package expression;

import java.util.Arrays;
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
	private boolean elseIf;
	public Else(String statement, int currentLine, Map<String, StringContainer> strings)
	{
		super(statement, Instruction.ELSE, currentLine,strings);
		if(elseIf && (!(this.statements.get(0) instanceof If)))
		{
			addError(Error.SomethingWrongWithElseIf);
			elseIf = false;
		}
		area = 0;
	}
	public String toString()
	{
		return branch+"Else";
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
	@Override
	public void splitBlock(Instruction instruction, int currentLine, String in) throws WrongComplexException {
		String wholeInstruction = in;
		String header;
		int i = 0;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction)).matcher(in);
		int lineBeforeStatement;
		if (checkBeginning.find()) {
			header = checkBeginning.group();
			in = in.replace(header, "");
			lineBeforeStatement = ParseUtils.getLines(header);
			this.line = currentLine + lineBeforeStatement;
		} 
		else
		{
			checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction), Pattern.CASE_INSENSITIVE).matcher(in); 
			if (checkBeginning.find()) {
				this.addError(Error.RestrictedLowerCase);
				header = checkBeginning.group();
				in = in.replace(header, "");
				lineBeforeStatement = ParseUtils.getLines(header);
				this.line = currentLine + lineBeforeStatement;
			} 
			else
				throw new WrongComplexException(Error.InvalidBeginning, wholeInstruction);
		}
		Matcher matcherId = Patterns.id.matcher(in);
		if(matcherId.find())
		{
			elseIf = true; 
			content = in;
			this.beginOfStatements = this.line;//+ ParseUtils.getLinesBNS(in);
		}
		else
		{
			Matcher states = Patterns.states.matcher(in);
			if (states.find()) 
			{
				this.beginOfStatements = this.line + ParseUtils.getLinesBNS(in);
				this.content = states.group();
			} 
			else
			{	// To moze byc nieprzyjemne. Sprawdzam jeszcze czy przypadkiem nie mam tam ifa zeby nie dodawac niepotrzebnie dwoch invalid blokow. Pozdro.
				Matcher matcherIf = Patterns.If.matcher(in);
				if(matcherIf.find())
				{
					this.beginOfStatements = this.line;// + ParseUtils.getLinesBNS(in);
					this.content = in;
				}
				else
					throw new WrongBlockException(wholeInstruction, in.substring(i + 1), this.line, this.area);
			}
		}
	}
}