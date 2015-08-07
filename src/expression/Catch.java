package expression;

import java.util.Map;

import Atoms.StringContainer;
import enums.Instruction;
import parser.ExpressionParser;

public class Catch extends ComplexExpression {

	public Catch(String name, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser){
		super(name, Instruction.CATCH, currentLine, strings);
		//Do zmiany
		if(content != null)
			statements = expressionParser.parseExpressions(content, beginOfStatements);
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
