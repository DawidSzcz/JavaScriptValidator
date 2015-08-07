package expression;

import java.util.Map;

import Atoms.StringContainer;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import parser.ExpressionParser;

public class While extends ComplexExpression{
	public While(String statement, int currentLine, Map<String, StringContainer> strings) 
	{
		super(statement, Instruction.WHILE, currentLine, strings);
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index - 1);
	}
	@Override
	public String toString() {
		return branch + "While";
	}
	@Override
	public boolean isValid() {
		try{
			if(condition != null)
				condition.isValid();
			else 
				return false;
			return true;
		}catch(InvalidOperator e)
		{
			this.addError(e.getError());
			return false;
		}catch(InvalidFunction e){
			this.addError(e.getError());
			return false;
		}
	}
}
