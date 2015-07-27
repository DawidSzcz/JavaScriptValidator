package expression;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import enums.Instruction;
import exception.WrongComplexException;
import exception.WrongIfException;
import exception.WrongTryException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;

public class Try extends ComplexExpression
{
	List<Catch> catchList = new LinkedList();
	Statement condition;
	public Try(String name, int currentLine, Map<String, String> strings, ExpressionParser expressionParser) throws IOException, WrongTryException 
	{
		super(name, currentLine, strings);
		try{
			Pair<String, String> divided = ParseUtils.splitBlock(Instruction.TRY, name);
			condition = new Statement(divided.getKey());
			statements = expressionParser.parseExpressions(divided.getValue());
			
		}catch(WrongComplexException e){
			throw new WrongTryException(e.getError(), e.getStatement());			
		}
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
