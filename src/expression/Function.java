package expression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import enums.Error;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongComplexException;
import exception.WrongForException;
import exception.WrongFunctionException;
import javafx.util.Pair;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;
import parser.StringContainer;

public class Function extends ComplexExpression {
	List<Statement>  arguments = new LinkedList(); 
	public Function(String statement, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser) throws WrongFunctionException, IOException 
	{
		super(statement, currentLine, strings);
		List <String> args;
		try{
		Pair<String, String> divided = ParseUtils.splitBlock(Instruction.FUNCITON, statement);
		
		args=Arrays.asList(divided.getKey());
		for (String arg:args)
			arguments.add(new Statement(arg));
		
		this.statements = expressionParser.parseExpressions(divided.getValue());
		}catch(WrongComplexException e){
			this.addError(e.getError());
			throw new WrongFunctionException(e.getError(), e.getStatement());
		}
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index -1);
	}
	@Override
	public String toString() {
		return "Function";
	}
	@Override
	public boolean isValid() {
		if (!super.isValid()){
			return false;
		}
		for(Statement condtioniterator:arguments){
			try {
				condtioniterator.isValid();
			} catch (InvalidOperator e) {
				return false;
			} catch (InvalidFunction e) {
				return false;
			}
		}
		return true;
	}
}
