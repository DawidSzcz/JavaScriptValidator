package expression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import enums.Error;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongFunctionException;

public class Function extends ComplexExpression {
	List<Statement>  arguments = new LinkedList(); 
	public Function(String statement, int currentLine, Map<String, String> strings, ExpressionParser expressionParser) throws WrongFunctionException, IOException 
	{
		super(statement, currentLine, strings);
		List <String> args;
		String statements;
		Matcher matcherArguments = Patterns.arg.matcher(statement);
		Matcher matcherStatement= Patterns.states.matcher(statement);
		
		if (matcherArguments.find()){
			args=Arrays.asList(matcherArguments.group().split(","));
		}
		else{
			throw new WrongFunctionException(enums.Error.InvalidArguments,statement);
		}
		
		if (matcherStatement.find()){
			statements = matcherStatement.group();
		}
		else{
			throw new WrongFunctionException(enums.Error.InvalidBlock,statement);
		}
		this.statements = expressionParser.parseExpressions(statements);
		for (String arg:args){
			arguments.add(new Statement(arg));
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
				if (!condtioniterator.isValid()){
					return false;
				}
			} catch (InvalidOperator e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (InvalidFunction e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
