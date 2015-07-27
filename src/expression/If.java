package expression;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import enums.Error;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongComplexException;
import exception.WrongForException;
import exception.WrongIfException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;
import parser.StringContainer;

public class If extends ComplexExpression{
	Statement condition;
	public If(String statement, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser) throws WrongIfException, IOException {
		super(statement, currentLine, strings);
		try{
		Pair<String, String> divided = ParseUtils.findCondition("if", statement);
		condition = new Statement(divided.getKey());
		this.statements = expressionParser.parseExpressions(divided.getValue());
		}catch(WrongComplexException e){
			this.addError(e.getError());
			throw new WrongIfException(e.getError(), e.getStatement());
		}
	}
	public If(String name, int currentLine, Statement condition2, Map<String, StringContainer> strings, List<Expression> statements) {
		super(name, currentLine, strings);
		this.condition = condition2;
		this.statements= statements;
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
		if(!super.isValid())
			return false;
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
