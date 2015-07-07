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
	
	public List<Expression> parse(String input) throws IOException, WrongWhileException
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
		String[] statements = input.split(Patterns.splitS);
		Matcher matcher;
		for(String statement : statements)
		{
			matcher = Patterns.id.matcher(statement);
			if(matcher.find())
			{
				statement = blocks.get(matcher.group());
			}
			Matcher matcherIf = Patterns.If.matcher(statement);
			Matcher matcherFunc = Patterns.function.matcher(statement);
			Matcher matcherWhile = Patterns.While.matcher(statement);
			Matcher matcherAssign = Patterns.assign.matcher(statement);
			Matcher matcherInvo = Patterns.invocation.matcher(statement);
			try{
				if(matcherIf.find())
					exps.add(makeIf(statement));
					else if(matcherFunc.find())
							exps.add(makeFunc(statement));
						else if(matcherWhile.find())
								exps.add(makeWhile(statement));
							else if(matcherAssign.find())
									exps.add(makeAssignment(statement));
								else if(matcherInvo.find())
										exps.add(makeInvocation(statement));
									else
										exps.add(new UnknownExpression(statement));
			}catch(WrongWhileException e){
				InvalidExpression exp = new InvalidExpression(e.getStatement());
				exp.addError(e.getError());
				exps.add(exp);
				exps.addAll(secondExpression(exp, statement));
			}
		}
		return exps;
	}
	private List<Expression> secondExpression(Expression exp, String statement) throws IOException, WrongWhileException {
		if(statement.contains("{"))
		{
			exp.addError("Missing closing bracket");
			return parseExpressions(statement.split("\\{")[1]);
		}
		Matcher match = Patterns.checkOpenning.matcher(statement);
		if(match.find())
		{
			exp.addError("Missing opening bracket");
			match = Patterns.line.matcher(statement);
			match.find();
			match.find();
			return parseExpressions(match.group());
		}
		return new LinkedList<Expression>();

		
	}
	private Expression makeFunc(String block) {
		// TODO Auto-generated method stub
		return null;
	}
	private Expression makeWhile(String group) throws IOException, WrongWhileException {
 		Matcher arg = Patterns.arg.matcher(group);
		Matcher states = Patterns.states.matcher(group);
		String arguments, statesments;
		if(arg.find())
			arguments = arg.group();
		else
			throw new WrongWhileException("Ivalid arguments", group);
		if(states.find())
			statesments = states.group();
		else
		{
			throw new WrongWhileException("Invalid block", group);
		}
			
		
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
