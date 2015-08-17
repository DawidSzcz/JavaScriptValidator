package expression;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import parser.ExpressionParser;

public class Try extends ComplexExpression
{
	List<Catch> catchList = new LinkedList();
	Statement condition;
	public Try(String name, int currentLine, String branch)
	{
		super(name, currentLine);
		this.branch = branch;
	}

	@Override
	public String toString() {
		if(catchList.isEmpty())
			return branch + "TRY ";
		else
			return branch + catchList.get(catchList.size()-1).toString();
	}

	@Override
	public boolean isValid() {
		super.isValid();
		if(!catchList.isEmpty())
			return true;
		this.addError(Error.TryWithNoCatch);
		return false;
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

}
