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
		return branch + "TRY";
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

}
