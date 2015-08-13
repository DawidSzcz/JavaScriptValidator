package expression;

import java.util.List;
import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import parser.ExpressionParser;
import validator.Context;

public class If extends ComplexExpression{
	public If(String statement, int currentLine, Map<String, StringContainer> strings, List<String> labels, String branch) {
		super(statement, Instruction.IF, currentLine, strings, labels, branch + "If ");
		this.branch = branch;
	}

	@Override
	public String toString() {
		return branch + "IF ";
	}
	protected Statement getCondition()
	{
		return condition;
	}
	protected List<Expression> getStatements()
	{
		return statements;
	}
	protected String getName()
	{
		return name;
	}
	@Override
	public boolean isValid() {
		try{
			if(condition != null)
				condition.isValid();
		}catch(InvalidOperator | InvalidFunction e)
		{
			this.addError(e.getError());
			return false;
		}
		return true;
	}
}
