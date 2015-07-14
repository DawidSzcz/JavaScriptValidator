package expression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import enums.Error;
import exception.JSValidatorException;
import exception.WrongElseException;
import exception.WrongIfException;

public class ExpressionParser {
	HashMap<String, String> blocks = new HashMap<>();
	List<String> instructions;
	public ExpressionParser(String input)
	{
		instructions = Arrays.asList(input.split("\n"));
		
	}
	public List<Expression> parse(String input) throws IOException {
		Matcher mat = Patterns.block.matcher(input);
		while (mat.find()) {
			String block = mat.group();
			String uniqueId = ParseUtils.uniqueId(input);
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
			int line = ParseUtils.getLine(instructions, statement);
			Matcher matcherIf = Patterns.If.matcher(statement);
			Matcher matcherFunc = Patterns.function.matcher(statement);
			Matcher matcherWhile = Patterns.While.matcher(statement);
			Matcher matcherAssign = Patterns.assign.matcher(statement);
			Matcher matcherInvo = Patterns.invocation.matcher(statement);
			Matcher matcherFor = Patterns.For.matcher(statement);
			Matcher matcherElse = Patterns.Else.matcher(statement);
			try {
				if(matcherElse.find())
					try{
					exps.add(new Else(statement, line, (If)exps.remove(exps.size()-1), this));
					}catch(Exception e){
						throw new WrongElseException(Error.MissingIfBeforeElse, statement);
					}
				else if (matcherIf.find())
						exps.add(new If(statement, line, this));
					else if (matcherFunc.find())
							exps.add(new Function(statement, line, this));
						else if (matcherWhile.find())
								exps.add(new While(statement, line, this));
							else if (matcherFor.find())
									exps.add(new For(statement, line, this));
								else if (matcherAssign.find())
										exps.add(new Assignment(statement, line));
									else if (matcherInvo.find())
											exps.add(new Invocation(statement, line));
										else {
												Expression unknown = new UnknownExpression(statement, instructions.indexOf(statement));
												exps.add(unknown);
												if (statement.contains("}"))
													unknown.addError(Error.UnexpectedClosingBracket);
											}
			} catch (JSValidatorException e) {
				InvalidExpression exp = new InvalidExpression(e.getStatement(), ParseUtils.getLine(instructions, statement));
				exp.addError(e.getError());
				exps.add(exp);
				exps.addAll(secondExpression(exp, statement));
			}
		}
		return exps;
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

}
