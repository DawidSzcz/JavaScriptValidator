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
import exception.WrongIfException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.ParseUtils.Triple;
import parser.Patterns;

public class If extends ComplexExpression{
	public If(String statement, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser) {
		super(statement, Instruction.IF, currentLine, strings);
		this.statements = expressionParser.parseExpressions(content, beginOfStatements);
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
		return "IF ";
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
			condition.isValid();
		}catch(InvalidOperator e)
		{
			this.addError(e.getError());
			return false;
		}catch(InvalidFunction e){
			this.addError(e.getError());
			return false;
		}
		return true;
	}
}
