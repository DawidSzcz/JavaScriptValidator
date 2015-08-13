package expression;

import java.util.List;
import java.util.Map;

import Atoms.StringContainer;
import enums.Instruction;
import parser.ExpressionParser;

public class Catch extends ComplexExpression {

	public Catch(String name, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser, List<String> labels, String branch){
		super(name, Instruction.CATCH, currentLine, strings, labels, branch + "Catch ");
		this.branch = branch;
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
