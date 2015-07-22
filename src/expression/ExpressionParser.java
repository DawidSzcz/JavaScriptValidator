package expression;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;
import java.util.regex.Matcher;

import ValidatorM.ValidationM;
import enums.Error;
import exception.JSValidatorException;
import exception.WrongElseException;

import javafx.util.Pair;

public class ExpressionParser {
	private HashMap<String, String> blocks = new HashMap<>();
	private List<String> instructions;
	private Map<String, String> strings;
	private String input;
	private int currentLine = 0;
	
	public ExpressionParser(String input)
	{
		instructions = Arrays.asList(input.split("\n"));
		input = ParseUtils.removeComments(input);
		Pair<String, Map<String, String>> pair = ParseUtils.takeOutStrings(input);
		this.input = pair.getKey();
		strings = pair.getValue();
	}
	public Expression parse() throws IOException {
		String wholeProgram = input;
		Matcher mat = Patterns.block.matcher(input);
		while (mat.find()) {
			String block = mat.group();
			String uniqueId = "BlockID"+ParseUtils.uniqueId(input);
			if (input.contains(block))
				input = input.replace(block, uniqueId + ";\n");
			blocks.put(uniqueId, block);
			mat = Patterns.block.matcher(input);
		}
		return new Program(wholeProgram, 0 , parseExpressions(input), strings);
	}
	List<Expression> parseExpressions(String input) throws IOException {
		List<Expression> exps = new LinkedList<>();
		String[] statements = input.split(Patterns.splitS);
		Matcher matcher;
		for (String statement : statements) {
			matcher = Patterns.id.matcher(statement);
			if (matcher.find()) {
				statement = blocks.get(ParseUtils.cleanLine(matcher.group()));
			}
			Matcher matcherIf = Patterns.If.matcher(statement);
			Matcher matcherFunc = Patterns.function.matcher(statement);
			Matcher matcherWhile = Patterns.While.matcher(statement);
			Matcher matcherAssign = Patterns.assign.matcher(statement);
			Matcher matcherInvo = Patterns.invocation.matcher(statement);
			Matcher matcherFor = Patterns.For.matcher(statement);
			Matcher matcherElse = Patterns.Else.matcher(statement);
			
			Expression exp;
			
			try {
				if(matcherElse.find())
					try{
						exp =new Else(statement, currentLine, (If)exps.remove(exps.size()-1), strings, this);
					}catch(Exception e){
						throw new WrongElseException(Error.MissingIfBeforeElse, statement);
					}
				else if (matcherIf.find())
						exp = new If(statement, currentLine, strings, this);
					else if (matcherFunc.find())
							exp = new Function(statement, currentLine, strings, this);
						else if (matcherWhile.find())
								exp = new While(statement, currentLine, strings, this);
							else if (matcherFor.find())
									exp = new For(statement, currentLine, strings, this);
								else if (matcherAssign.find())
										exp = new Assignment(statement, currentLine, strings);
									else if (matcherInvo.find())
											exp = new Invocation(statement, currentLine, strings);
										else {
												exp = new UnknownExpression(statement, currentLine, strings);
												if (statement.contains("}"))
													exp.addError(Error.UnexpectedClosingBracket);
											}
			} catch (JSValidatorException e) {
				exp = new InvalidExpression(e.getStatement(), currentLine, strings);
				exp.addError(e.getError());
				exps.add(exp);
				exps.addAll(secondExpression(exp, statement));
				continue;
			}
			currentLine = exp.setLine(instructions);
			exp.isValid();
			exps.add(exp);
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
