package expression;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Instruction;
import exception.WrongComplexException;
import exception.WrongIfException;
import exception.WrongTryException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.ParseUtils.Triple;

public class Try extends ComplexExpression
{
	List<Catch> catchList = new LinkedList();
	Statement condition;
	public Try(String name, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser) throws  WrongComplexException
	{
		super(name, Instruction.TRY, currentLine, strings);
			statements = expressionParser.parseExpressions(content, beginOfStatements);
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
		return "TRY";
	}

	@Override
	public boolean isValid() {
		return true;
	}
	public void insertCatch(Catch c)
	{
		catchList.add(c);
	}

}
