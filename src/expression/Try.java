package expression;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import parser.ExpressionParser;
import parser.Patterns;

public class Try extends ComplexExpression
{
	List<Catch> catchList = new LinkedList();
	Statement condition;
	public Try(String name, int currentLine, List<String> labels, String branch)
	{
		super(name, currentLine, labels);
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.TRY)).matcher(name);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
		this.branch = branch;
	}

	@Override
	public String getBranch() {
		if(catchList.isEmpty())
			return super.getBranch();
		else
			return branch + catchList.get(catchList.size()-1).getBranch();
	}

	@Override
	public boolean isValid() {
		boolean valid = super.isValid();
		if(catchList.isEmpty())
		{
			valid = false;
			this.addError(Error.TryWithNoCatch, line);
		}
		return valid;
	}
	public void insertCatch(Catch c)
	{
		catchList.add(c);
	}
	@Override
	public void addtoInstructions(Map<Integer, List<Expression>> instructions) {
		super.addtoInstructions(instructions);
		for (Catch exp : catchList)
			exp.addtoInstructions(instructions);
	}
	@Override
	public void insertBlock(List<Expression> block)
	{
		if(catchList.isEmpty())
			super.insertBlock(block);
		else
			catchList.get(catchList.size()-1).insertBlock(block);
	}
	@Override
	public int nextLine()
	{
		if(catchList.isEmpty())
			return super.nextLine();
		else
			return catchList.get(catchList.size()-1).nextLine();
	}

	@Override
	public String toString() {
		return branch + "TRY ";
	}

}
