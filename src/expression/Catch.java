package expression;

import java.util.List;
import java.util.Map;

import Atoms.StringContainer;
import enums.Instruction;
import parser.ExpressionParser;

public class Catch extends ComplexExpression {

	public Catch(String name, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser, List<String> labels){
		super(name, Instruction.CATCH, currentLine, strings, labels);
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		return statements.get(index-1);
	}

	@Override
	public String toString() {
		return branch + "CATCH";
	}

	@Override
	public boolean isValid() {
		return true;
	}

	public int size() {
		return statements.size() + 1;
	}

}
