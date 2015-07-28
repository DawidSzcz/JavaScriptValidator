package expression;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongAssignmentException;
import exception.WrongComplexException;
import exception.WrongForException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.ParseUtils.Triple;
import parser.Patterns;

public class For extends ComplexExpression{
	Expression[] forConditions = new Expression[3];

	public For(String statement, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser) throws IOException, WrongForException {
		super(statement, currentLine, strings);
		try{
			Triple divided = ParseUtils.splitBlock(Instruction.FOR, statement);
			line = currentLine + divided.lineBeforeStatement;
			String[] conditions = (divided.header+" ").split(";");
		
		if(conditions.length == 3)
		{
			for(int i = 0; i < 3; i++)
				if(!conditions[i].matches(Patterns.empty))
				{
					List<Expression> list = expressionParser.parseExpressions(conditions[i], 0);
					if(list.size() != 1)
						throw new WrongForException(Error.InvalidForCondition, statement);
					else
						forConditions[i] = list.get(0);
				}
				else
					forConditions[i] = new EmptyExpression();			
		}
		else
			throw new WrongForException(Error.WrongNumberOfArguments, statement);
		this.statements = expressionParser.parseExpressions(divided.statements, currentLine + divided.lines);
		}catch(WrongComplexException e)
		{
			this.addError(e.getError());
			throw new WrongForException(e.getError(), e.getStatement());
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
		return "For";
	}

	@Override
	public boolean isValid() {
	boolean isValid = true;
	for(Expression e : forConditions)
		if(!e.isValid())
			isValid = false;
	return isValid;
	}
	@Override
	public boolean hasErrors() {
		for(Expression e : forConditions)
			if(e.hasErrors())
				return true;
		return false;
	}
	@Override
	public List<Error> getErrors() {
		List<Error> errs= super.getErrors();
		for(Expression e : forConditions)
			errs.addAll(e.getErrors());
		return errs;
	}

}