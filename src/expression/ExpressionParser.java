package expression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.coyote.ErrorState;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;

import enums.Error;
import exception.WrongComplexException;
import exception.WrongForException;
import exception.WrongIfException;
import exception.WrongWhileException;

public class ExpressionParser {
	HashMap<String, String> blocks = new HashMap<>();
	List<String> instructions;
	public List<Expression> parse(String input) throws IOException {
		instructions = Arrays.asList(input.split("\n"));
		Matcher mat = Patterns.block.matcher(input);
		while (mat.find()) {
			String block = mat.group();
			String uniqueId = uniqueId(input);
			if (input.contains(block))
				input = input.replace(block, uniqueId + ";\n");
			blocks.put(uniqueId, block);
			mat = Patterns.block.matcher(input);
		}
		return parseExpressions(input);
	}
	private List<Expression> parseExpressions(String input) throws IOException {
		List<Expression> exps = new LinkedList<>();
		String[] statements = input.split(Patterns.splitS);
		Matcher matcher;
		for (String statement : statements) {
			matcher = Patterns.id.matcher(statement);
			if (matcher.find()) {
				statement = blocks.get(matcher.group());
			}
			Matcher matcherIf = Patterns.If.matcher(statement);
			Matcher matcherFunc = Patterns.function.matcher(statement);
			Matcher matcherWhile = Patterns.While.matcher(statement);
			Matcher matcherAssign = Patterns.assign.matcher(statement);
			Matcher matcherInvo = Patterns.invocation.matcher(statement);
			Matcher matcherFor = Patterns.For.matcher(statement);
			Matcher matcherElse = Patterns.Else.matcher(statement);
			try {
				if(matcherElse.find())
					exps.add(makeElse(statement, exps.remove(exps.size()-1)));
				else if (matcherIf.find())
						exps.add(makeIf(statement));
					else if (matcherFunc.find())
							exps.add(makeFunc(statement));
						else if (matcherWhile.find())
								exps.add(makeWhile(statement));
							else if (matcherFor.find())
									exps.add(makeFor(statement));
								else if (matcherAssign.find())
										exps.add(makeAssignment(statement));
									else if (matcherInvo.find())
											exps.add(makeInvocation(statement));
										else {
												Expression unknown = new UnknownExpression(statement, instructions.indexOf(statement));
												exps.add(unknown);
												if (statement.contains("}"))
													unknown.addError(Error.UnexpectedClosingBracket);
											}
			} catch (WrongComplexException e) {
				InvalidExpression exp = new InvalidExpression(e.getStatement(), getLine(statement));
				exp.addError(e.getError());
				exps.add(exp);
				exps.addAll(secondExpression(exp, statement));
			}
		}
		return exps;
	}

	private String cleanLine(String statement) {
		Matcher matcher = Patterns.line.matcher(statement);
		if(!matcher.find())
			return "";
		return matcher.group();
	}
	private Expression makeElse(String statement, Expression If) throws WrongIfException, IOException {
		Matcher states = Patterns.states.matcher(statement);
		String arguments, statesments;
		if (states.find())
			statesments = states.group();
		else {
			throw new WrongIfException(Error.InvalidBlock, statement);
		}
		return new Else(statement, getLine(statement), (If)If, parseExpressions(statesments));
	}

	private List<Expression> secondExpression(Expression exp, String statement) throws IOException {
		if (statement.contains("{")) {
			exp.addError(Error.UnexpectedOpeningBracket);
			return parseExpressions(statement.split("\\{")[1]);
		}
		Matcher match = Patterns.checkOpenning.matcher(statement);
		if (match.find()) {
			exp.addError(Error.MissingOpenningBracket);
			match = Patterns.line.matcher(statement);
			match.find();
			match.find();
			return parseExpressions(match.group());
		}
		return new LinkedList<Expression>();

	}

	private Expression makeFor(String group) throws IOException, WrongComplexException {
		Matcher arg = Patterns.arg.matcher(group);
		Matcher states = Patterns.states.matcher(group);
		String arguments,statesments; 
		if (arg.find())
			arguments = arg.group();
		else
			throw new WrongForException(Error.InvalidArguments, group);
		if (states.find())
			statesments = states.group();
		else {
			throw new WrongForException(Error.InvalidBlock, group);
		}

		return new For(group, getLine(group), arguments, parseExpressions(statesments));

	}

	private Expression makeFunc(String block) {
		// TODO Auto-generated method stub
		return null;
	}

	private Expression makeWhile(String group) throws IOException, WrongComplexException {
		Matcher arg = Patterns.arg.matcher(group);
		Matcher states = Patterns.states.matcher(group);
		String arguments, statesments;
		if (arg.find())
			arguments = arg.group();
		else
			throw new WrongWhileException(Error.InvalidArguments, group);
		if (states.find())
			statesments = states.group();
		else {
			throw new WrongWhileException(Error.InvalidBlock, group);
		}

		return new While(group, getLine(group), arguments, parseExpressions(statesments));

	}

	private Expression makeIf(String group) throws IOException, WrongComplexException {
		Matcher arg = Patterns.arg.matcher(group);
		Matcher states = Patterns.states.matcher(group);
		String arguments, statesments;
		if (arg.find())
			arguments = arg.group();
		else
			throw new WrongIfException(Error.InvalidArguments, group);
		if (states.find())
			statesments = states.group();
		else {
			throw new WrongIfException(Error.InvalidBlock, group);
		}
		return new If(group, getLine(group), arguments, parseExpressions(statesments));

	}

	private int getLine(String group) {
		group = cleanLine(group);
		for(int i = 0; i < instructions.size(); i++)
			try{
				if(group.contains(cleanLine(instructions.get(i))))
					return i + 1;
			}catch(IllegalStateException e){}
		return -2;
	}
	private Expression makeInvocation(String statement) {
		return new Invocation(statement, getLine(statement));
	}

	private Expression makeAssignment(String stat) {
		Matcher mat = Patterns.clean.matcher(stat);
		mat.find();
		stat = mat.group();
		String side[] = stat.split("=");
		return new Assignment(stat, getLine(stat),side[0], side[1]);
	}

	public static String uniqueId(String in) {
		Random rand = new Random();
		String randomString = String.valueOf(rand.nextLong());
		while (in.contains(randomString))
			;
		randomString = String.valueOf(rand.nextLong());
		return randomString;
	}
}
