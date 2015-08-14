package expression;

import java.util.List;
import java.util.Map;

import Atoms.StringContainer;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import parser.ExpressionParser;

public class While extends ComplexExpression{
	public While(String statement, int currentLine, List<String> labels, String branch) 
	{
		super(statement, currentLine);
		this.branch = branch;
	}

	@Override
	public String toString() {
		return branch + "While ";
	}
	@Override
	public boolean isValid() {
		super.isValid();
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
