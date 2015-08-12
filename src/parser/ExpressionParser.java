package parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.InputContainer;
import Atoms.StringContainer;
import enums.Error;
import expression.Assignment;
import expression.Catch;
import expression.ControlExpression;
import expression.Else;
import expression.Expression;
import expression.For;
import expression.Function;
import expression.If;
import expression.InvalidComment;
import expression.Invocation;
import expression.Switch;
import expression.Try;
import expression.UnknownExpression;
import expression.While;
import javafx.util.Pair;

public class ExpressionParser {
	private HashMap<String, String> blocks = new HashMap<>();
	private Map<String, StringContainer> strings;
	private InputContainer input;
	private String wholeProgram;
	private List<String> labels;
	
	public ExpressionParser(String input)
	{
		//Pair <String, HashMap<String, StringContainer>> pair =ParseUtils.removeStrAndCom(input);
		Pair <InputContainer, HashMap<String,StringContainer>> removedStrings=ParseUtils.takeOutStringsAndComents(input);
		//Pair<String, List<String>> removedLabels = ParseUtils.removeLabels(removedStrings.getKey().string);
		Pair<String, HashMap<String, String>> removedBlocks = ParseUtils.removeBlocks(removedStrings.getKey().string);
		
		this.input = removedStrings.getKey();
		this.input.string = removedBlocks.getKey();
		wholeProgram = removedStrings.getKey().string;
		blocks = removedBlocks.getValue();
		strings = removedStrings.getValue();
		//labels = removedLabels.getValue();
	}
	public List<Expression> parse() {		
		List<Expression> list = parseExpressions(input.string, 1, new LinkedList<String>(), "");
		if(input.getErrors().size() != 0)
			list.add(new InvalidComment(wholeProgram, input.getErrors(), input.getLine()));
		return list;
	}
	public List<Expression> parseExpressions(String input, int currentLine, List<String> labels, String branch) {
		List<Expression> exps = new LinkedList<>();
		String[] statements = input.split(Patterns.splitS);
		Matcher matcher;
		int labelCount = labels.size();
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
			Matcher matcherSwich = Patterns.Switch.matcher(statement);	
			Matcher matcherControl= Patterns.control.matcher(statement);
			
			Expression exp;
			
			if(matcherElse.find())
			{
				exp =new Else(statement, currentLine, strings, labels.subList(0, labelCount), branch);
				if(!(exps.get(exps.size()-1) instanceof If) && !(exps.get(exps.size()-1) instanceof Else && ((Else)exps.get(exps.size()-1)).isElseIf()))
					exp.addError(Error.MissingIfBeforeElse);
			}
			else if (matcherCatch.find())
			{
				exp = new Catch(statement, currentLine, strings, this, labels.subList(0, labelCount), branch);
				try{
					((Try)exps.get(exps.size()-1)).insertCatch((Catch)exp);
					exp = exps.remove(exps.size()-1);
				}catch(Exception e){
					exp.addError(Error.MissingTryBeforeCatch);
				}
			}
				else if (matcherIf.find())
						exp = new If(statement, currentLine, strings, labels.subList(0, labelCount), branch);
					else if (matcherFunc.find())
							exp = new Function(statement, currentLine, strings, labels.subList(0, labelCount), branch);
						else if (matcherWhile.find())
								exp = new While(statement, currentLine, strings, labels.subList(0, labelCount), branch);
							else if (matcherFor.find())
									exp = new For(statement, currentLine, strings, labels.subList(0, labelCount), branch);
								else if (matcherTry.find())
										exp = new Try(statement, currentLine, strings, labels.subList(0, labelCount), branch);
									else if(matcherSwich.find())
											exp = new Switch(statement, currentLine, strings, labels.subList(0, labelCount), branch);
	 									else if (matcherControl.find())
												exp = new ControlExpression(statement, currentLine, strings, labels.subList(0, labelCount), branch);
											else if (matcherAssign.find())
													exp = new Assignment(statement, currentLine, strings, branch);
												else if (matcherInvo.find())
														exp = new Invocation(statement, currentLine, strings, branch);
													else {
															if(statement.matches("\\s*"))
																continue;
															exp = new UnknownExpression(statement, currentLine, strings, branch);
															if (statement.contains("}"))
																exp.addError(Error.UnexpectedClosingBracket);
														}
			exps.add(exp);
			currentLine+=ParseUtils.getLines(statement, blocks);
		}
		return exps;
	}



}
