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
	public Try(String name, int currentLine, Map<String, StringContainer> strings, List<String> labels, String branch)
	{
		super(name, Instruction.TRY, currentLine, strings, labels, branch + "Try ");
		this.branch = branch;
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		if(index <= this.statements.size())
			return statements.get(index-1);
		return catchList.get(index- statements.size() -1 ).get(0);
	}

	@Override
	public String toString() {
		return branch + "TRY";
	}

	@Override
	public boolean isValid() {
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
