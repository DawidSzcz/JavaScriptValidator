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
import expression.Block;
import expression.Catch;
import expression.ComplexExpression;
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
import expression.Var;
import expression.While;
import javafx.util.Pair;

public class ExpressionParser {
	private Map<String, String> blocks = new HashMap<>();
	private InputContainer input;
	private String wholeProgram;
	private List<String> labels;
	
	public ExpressionParser(String input)
	{
		this.input=ParseUtils.takeOutStringsAndComents(input);
		Pair<String, Map<String, String>> removedBlocks = ParseUtils.removeBlocks(this.input.getString());
		wholeProgram = this.input.getString();
		this.input.setString(removedBlocks.getKey());
		blocks = removedBlocks.getValue();
	}
	public List<Expression> parse() {		
		List<Expression> list = parseExpressions(input.getString(), 1, new LinkedList<String>(), "");
		if(input.getErrors().size() != 0)
			list.add(new InvalidComment(wholeProgram, input.getErrors(), input.getLine()));
		return list;
	}
	public List<Expression> parseExpressions(String input, int currentLine, List<String> labels, String branch) {
		List<Expression> exps = new LinkedList<>();
		String[] statements = input.split(Patterns.splitS);
		int labelCount = labels.size();
		for (String statement : statements) {
			Matcher matcherAssign = Patterns.assign.matcher(statement);
			Matcher matcherInvo = Patterns.invocation.matcher(statement);
			Matcher matcherControl= Patterns.control.matcher(statement);
			Matcher matcherVar = Patterns.Var.matcher(statement);
			Matcher matchHead = Patterns.headerId.matcher(statement);
			Matcher matchBlock = Patterns.id.matcher(statement);
			
			Expression exp = null;
			if(matchHead.find() && blocks.containsKey(ParseUtils.cleanLine(matchHead.group())))
			{
				currentLine+= ParseUtils.getLines(statement);
				String head = ParseUtils.cleanLine(matchHead.group());
				statement = blocks.get(head);
				if(head.startsWith("else"))
				{
					exp =new Else(statement, currentLine, branch);
					if(!(exps.get(exps.size()-1) instanceof If) && !(exps.get(exps.size()-1) instanceof Else && ((Else)exps.get(exps.size()-1)).isElseIf()))
						exp.addError(Error.MissingIfBeforeElse, currentLine);
				}
				else if (head.startsWith("catch"))
				{
					exp = new Catch(statement, currentLine, branch);
					try{
						((Try)exps.get(exps.size()-1)).insertCatch((Catch)exp);
						exp = exps.remove(exps.size()-1);
					}catch(Exception e){
						exp.addError(Error.MissingTryBeforeCatch, currentLine);
					}
				}
					else if (head.startsWith("if"))
					{
						exp = new If(statement, currentLine, branch);
						if(exps.size() > 0 && exps.get(exps.size() -1) instanceof Else && ((Else)exps.get(exps.size() -1)).isEmpty())
						{
							((Else)exps.get(exps.size() -1)).addIf(exp);
							exp = exps.remove(exps.size() -1);
						}
					}
						else if (head.startsWith("function"))
								exp = new Function(statement, currentLine, branch);
							else if (head.startsWith("while"))
									exp = new While(statement, currentLine, labels.subList(0, labelCount), branch);
								else if (head.startsWith("for"))
										exp = new For(statement, currentLine, labels.subList(0, labelCount), branch, this);
									else if (head.startsWith("try"))
											exp = new Try(statement, currentLine, branch);
										else if(head.startsWith("switch"))
												exp = new Switch(statement, currentLine, labels.subList(0, labelCount), branch);
			}		
			else if (matchBlock.find() && blocks.containsKey(ParseUtils.cleanLine(matchBlock.group()))) {
				String blockID = ParseUtils.cleanLine(matchBlock.group());
				statement = statement.replace(blockID,blocks.get(blockID));
				String states = statement;
				try{
					Matcher statesMatch = Patterns.states.matcher(ParseUtils.cleanLine(statement));
					if(statesMatch.find())
						states = statesMatch.group();
				}catch(IllegalStateException e){}
				
				try{
					exp = (ComplexExpression)exps.get(exps.size()-1);
					exps.remove(exps.size()-1);
				}catch(Exception e)
				{
					exp = new Block(statement, currentLine, branch);
				}
				
				((ComplexExpression)exp).insertBlock(this.parseExpressions(states, ((ComplexExpression)exp).nextLine() + ParseUtils.getLinesBNS(statement), labels, exp.getBranch()));
			}													
				else if (matcherVar.find())	
						exp = new Var(statement, currentLine, branch);
					else if (matcherControl.find())
							exp = new ControlExpression(statement, currentLine, labels.subList(0, labelCount), branch);
						else if (matcherAssign.find())
								exp = new Assignment(statement, currentLine, branch);
							else if (matcherInvo.find())
									exp = new Invocation(statement, currentLine, branch);
								else {
										if(statement.matches("\\s*"))
											continue;
										exp = new UnknownExpression(statement, currentLine, branch);
										if (statement.contains("}"))
											exp.addError(Error.UnexpectedClosingBracket, currentLine);
									}
			exps.add(exp);
			currentLine+=ParseUtils.getLines(statement, blocks);
		}
		return exps;
	}



}
