package expression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exception.WrongWhileException;

public class ExpressionParser 
{
	HashMap<String, String> blocks = new HashMap<>(); 
	
	public List<Expression> Parse(String input) throws IOException, WrongWhileException
	{
		Matcher mat = Patterns.block.matcher(input);
		while(mat.find())
		{
			String block = mat.group();
			String uniqueId = uniqueId(input);
			if(input.contains(block))
				input = input.replace(block, uniqueId+";\n");
			blocks.put(uniqueId, block);
			mat = Patterns.block.matcher(input);
		}
		return parseExpressions(input);
	}
	private List<Expression> parseExpressions(String input) throws IOException, WrongWhileException
	{
		List<Expression> exps= new LinkedList<>();
		String[] statements = input.split(";\\s*");
		Matcher matcher;
		for(String statement : statements)
		{
			matcher = Patterns.id.matcher(statement);
			if(matcher.find())
			{
				String block = blocks.get(matcher.group());
				matcher = Patterns.If.matcher(block);
				if(matcher.find())
					exps.add(makeIf(block));
				else
					exps.add(makeWhile(block));
			}
			else
			{
				matcher = Patterns.simple.matcher(statement);
				if(!matcher.find());
				{
					matcher = Patterns.assign.matcher(statement);
					if(matcher.find())
						exps.add(makeAssignment(statement));
					else
						exps.add(makeInvocation(statement));
				}
			}
		}
		return exps;
	}
	private Expression makeWhile(String group) throws IOException, WrongWhileException {
 		Matcher arg = Patterns.arg.matcher(group);
		Matcher states = Patterns.states.matcher(group);
		String arguments, statesments;
		if(arg.find())
			arguments = arg.group();
		else
			throw new WrongWhileException(group);
		if(states.find())
			statesments = states.group();
		else
			throw new WrongWhileException(group);
			
		
		return new While(arguments, parseExpressions(statesments));
		
	}
	private Expression makeIf(String group) throws IOException, WrongWhileException {
		Matcher arg = Patterns.arg.matcher(group);
		Matcher stats = Patterns.states.matcher(group);
		arg.find();
		stats.find();
		return new If(arg.group(), parseExpressions(stats.group()));
		
	}
	private Expression makeInvocation(String statement) {
		return new Invocation(statement);
	}
	private Expression makeAssignment(String stat) {
		Matcher mat = Patterns.clean.matcher(stat);
		mat.find();
		stat = mat.group();
		String side[] = stat.split("=");
		return new Assignment(side[0], side[1]);
	}
	public static String uniqueId(String in)
	{
		Random rand = new Random();
		String randomString = String.valueOf(rand.nextLong());
		while(in.contains(randomString));
			randomString = String.valueOf(rand.nextLong());
		return randomString;
	}
}
