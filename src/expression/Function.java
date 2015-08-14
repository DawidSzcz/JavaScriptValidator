package expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.InvalidExpression;
import exception.WrongComplexException;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;
import validator.Context;

public class Function extends ComplexExpression {
	List<Statement>  arguments = new LinkedList<Statement>(); 
	List <String> args;
	String functionName;
	public Function(String statement, int currentLine, String branch) 
	{
		super(statement, currentLine);
		this.branch = branch;
		args = makeArgs(statement);
		for (String arg:args)
			arguments.add(new Statement(arg));
		functionName = getName(statement);
		if (Context.functions.containsKey(functionName)){
			Context.functions.get(functionName).add(args.size());
		}else{
			List<Integer> list = new ArrayList<Integer>();
			list.add(args.size());
			Context.functions.put(functionName,list);
		}
	}

	@Override
	public String toString() {
		return branch + "Function";
	}
	@Override
	public boolean isValid() 
	{
		super.isValid();
		for(Statement condtioniterator:arguments){
			try {
				condtioniterator.isValid();
			} catch (InvalidExpression e) {
				return false;
			}
		}
		return true;
	}
	private String getName(String statement){
		statement = statement.substring(0, statement.indexOf('('));
		String name = statement.replaceAll("function", "");
		name = parser.ParseUtils.cleanLine(name);
		return name;
	}
	public List<String> makeArgs(String in)
	{
		List<String> list = new LinkedList<>();
		String currentArg ="";
		int opened = 1;
		for (int i = 0; i < in.length(); i++) {
			if(opened == 1 && in.charAt(i)== ',')
			{
				list.add(currentArg);
				currentArg = "";
				continue;
			}
			if (in.charAt(i) == '(')
				opened++;
			if (in.charAt(i) == ')')
				opened--;
			currentArg += in.charAt(i);
		}
		list.add(currentArg);
		return list;
	}
}
