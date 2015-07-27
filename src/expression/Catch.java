package expression;

import java.io.IOException;
import java.util.Map;

import enums.Instruction;
import exception.WrongCatchException;
import exception.WrongComplexException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.StringContainer;

public class Catch extends ComplexExpression {

	private Statement condition;
	public Catch(String name, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser) throws IOException, WrongCatchException {
		super(name, currentLine, strings);
		try {
			Pair<String, String> divided = ParseUtils.splitBlock(Instruction.CATCH, name);
			condition = new Statement(divided.getKey());
			statements = expressionParser.parseExpressions(divided.getValue());
		} catch (WrongComplexException e) {
			throw new WrongCatchException(e.getError(), e.getMessage());
		}
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		return statements.get(index-1);
	}

	@Override
	public String toString() {
		return "CATCH";
	}

	@Override
	public boolean isValid() {
		return true;
	}

	public int size() {
		return statements.size() + 1;
	}

}
