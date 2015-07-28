package expression;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongComplexException;
import exception.WrongForException;
import exception.WrongWhileException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.ParseUtils.Triple;
import parser.Patterns;

public class While extends ComplexExpression{
	Statement  condition;
	public While(String statement, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser) throws WrongWhileException, IOException 
	{
		super(statement, currentLine, strings);
		try{
			Triple divided = ParseUtils.splitBlock(Instruction.WHILE, statement);
			line = currentLine + divided.lines;
			condition = new Statement(divided.header);
			this.statements = expressionParser.parseExpressions(divided.statements, currentLine + divided.lineBeforeStatement);
		}catch(WrongComplexException e){
			this.addError(e.getError());
			throw new WrongWhileException(e.getError(), e.getStatement());
		}
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
		return "While";
	}
	@Override
	public boolean isValid() {
		try{
			return condition.isValid();
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
