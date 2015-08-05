package parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Error;
import expression.Assignment;
import expression.Catch;
import expression.Else;
import expression.Expression;
import expression.For;
import expression.Function;
import expression.If;
import expression.Invocation;
import expression.Try;
import expression.UnknownExpression;
import expression.While;
import javafx.util.Pair;

public class ExpressionParser {
	private HashMap<String, String> blocks = new HashMap<>();
	private Map<String, StringContainer> strings;
	private StringContainer input;
	
	public ExpressionParser(String input)
	{
		//Pair <String, HashMap<String, StringContainer>> pair =ParseUtils.removeStrAndCom(input);
		Pair <StringContainer, HashMap<String,StringContainer>>pair=ParseUtils.takeOutStringsAndComents(input);
		this.input = pair.getKey();
		strings = pair.getValue();
	}
	public List<Expression> parse() {
		Pair<String, HashMap<String, String>> pair = ParseUtils.removeBlocks(input.string);
		blocks = pair.getValue();
		input.string = pair.getKey();
		
		List<Expression> list = parseExpressions(input.string, 1);
		if(input)
	}
	public List<Expression> parseExpressions(String input, int currentLine) {
		List<Expression> exps = new LinkedList<>();
		String[] statements = input.split(Patterns.splitS);
		Matcher matcher;
		for (String statement : statements) {
			matcher = Patterns.id.matcher(statement);
			if (matcher.find()) {
				String blockID = ParseUtils.cleanLine(matcher.group());
				if(blocks.containsKey(blockID))
					statement = statement.replace(blockID,blocks.get(blockID));
			}
			Matcher matcherIf = Patterns.If.matcher(statement);
			Matcher matcherFunc = Patterns.function.matcher(statement);
			Matcher matcherWhile = Patterns.While.matcher(statement);
			Matcher matcherAssign = Patterns.assign.matcher(statement);
			Matcher matcherInvo = Patterns.invocation.matcher(statement);
			Matcher matcherFor = Patterns.For.matcher(statement);
			Matcher matcherElse = Patterns.Else.matcher(statement);
			Matcher matcherTry = Patterns.Try.matcher(statement);
			Matcher matcherCatch = Patterns.Catch.matcher(statement);
			
			Expression exp;
			
			if(matcherElse.find())
			{
				exp =new Else(statement, currentLine, strings, this);
				if(!(exps.get(exps.size()-1) instanceof If) && !(exps.get(exps.size()-1) instanceof Else && ((Else)exps.get(exps.size()-1)).isElseIf()))
					exp.addError(Error.MissingIfBeforeElse);
			}
			else if (matcherCatch.find())
			{
				exp = new Catch(statement, currentLine, strings, this);
				try{
					((Try)exps.get(exps.size()-1)).insertCatch((Catch)exp);
					exp = exps.remove(exps.size()-1);
				}catch(Exception e){
					exp.addError(Error.MissingTryBeforeCatch);
				}
			}
				else if (matcherIf.find())
						exp = new If(statement, currentLine, strings, this);
					else if (matcherFunc.find())
							exp = new Function(statement, currentLine, strings, this);
						else if (matcherWhile.find())
								exp = new While(statement, currentLine, strings, this);
							else if (matcherFor.find())
									exp = new For(statement, currentLine, strings, this);
								else if (matcherTry.find())
										exp = new Try(statement, currentLine, strings, this);
									else if (matcherAssign.find())
											exp = new Assignment(statement, currentLine, strings);
										else if (matcherInvo.find())
												exp = new Invocation(statement, currentLine, strings);
											else {
													if(statement.matches("\\s+"))
														continue;
													exp = new UnknownExpression(statement, currentLine, strings);
													if (statement.contains("}"))
														exp.addError(Error.UnexpectedClosingBracket);
												}
			exps.add(exp);
			currentLine+=ParseUtils.getLines(statement, blocks);
			exp.isValid();
		}
		return exps;
	}



}
