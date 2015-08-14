package expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongBlockException;
import exception.WrongComplexException;
import exception.WrongConditionException;
import parser.ParseUtils;
import parser.Patterns;
import validator.Context;

public abstract class ComplexExpression extends Expression {

	protected List<Expression> statements = null;
	protected int beginOfStatements;
	protected Statement condition;

	public ComplexExpression(String in,  int currentLine) {
		super(in, currentLine);
		Matcher matchCondition = Patterns.condition.matcher(in);
		Matcher matchElse = Patterns.condition.matcher(in);
		Matcher matchTry = Patterns.condition.matcher(in);
		if(matchCondition.find())
			condition = new Statement(matchCondition.group());
		else if(matchElse.find() || matchTry.find())
				condition = null;
			else
				this.addError(Error.InvalidCondition);
		beginOfStatements = line + area;

	}

	@Override
	public HashMap<Integer, List<Error>> getAllErrors() {
		HashMap<Integer, List<Error>> hash = new HashMap<>();
		if (!errors.isEmpty())
			hash.put(line, getErrors());
		if(statements != null)
			for (Expression exp : statements) {
				for (Integer l : exp.getAllErrors().keySet()) {
					if (!hash.keySet().contains(l))
						hash.put(l, exp.getAllErrors().get(l));
					else
						hash.get(l).addAll(exp.getAllErrors().get(l));
				}
			}
		return hash;
	}
	public void insertBlock( List<Expression> block)
	{
		this.statements = block;
	}
	@Override
	public void addtoInstructions(Map<Integer, List<Expression>> instructions) {
		super.addtoInstructions(instructions);
		if(statements != null)
			for (Expression exp : statements)
				exp.addtoInstructions(instructions);
	}
	public int nextLine()
	{
		return beginOfStatements;
	}
	@Override
	public boolean isValid()
	{
		if(statements == null)
		{
			this.addError(Error.InvalidBlock);
			return false;
		}
		return true;
	}

//	public void splitBlock(Instruction instruction, int currentLine, String in, List<String> labels) throws WrongComplexException {
//		String wholeInstruction = in;
//		List<Character> forbidden = Arrays.asList('{', '}', ';');
//		String header;
//		int i = 0;
//		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction)).matcher(in);
//		int opened = 1;
//		int instructionArea = 0, lineBeforeStatement;
//		if (checkBeginning.find()) {
//			header = checkBeginning.group();
//			in = in.replace(header, "");
//			lineBeforeStatement = ParseUtils.getLinesBNS(header); 	// Wczesniej ParseUtils.getLines(header); - zmienione bo zle znajdowa³em linie dla przypadku if\n( 
//																	// kolejna zmiana w przypisaniu do area. 
//			this.line = currentLine + lineBeforeStatement;
//		} 
//		else
//		{
//			checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction), Pattern.CASE_INSENSITIVE).matcher(in); 
//			if (checkBeginning.find()) {
//				this.addError(Error.RestrictedLowerCase);
//				header = checkBeginning.group();
//				in = in.replace(header, "");
//				lineBeforeStatement = ParseUtils.getLinesBNS(header); // j.w.
//				this.line = currentLine + lineBeforeStatement;
//			} 
//			else
//				throw new WrongComplexException(Error.InvalidBeginning, wholeInstruction);
//		}
//		Matcher labelM = Patterns.label.matcher(header);
//		if(header.contains(":") && labelM.find())
//		{
//			String label = labelM.group();
//			labels.add(ParseUtils.cleanLine(label.substring(0, label.length() -1)));
//		}
//
//		if (!instruction.equals(Instruction.TRY) && !instruction.equals(Instruction.ELSE)) {
//			for (i = 0; i < in.length(); i++) {
//				if (forbidden.contains(in.charAt(i)))
//					throw new WrongConditionException(Error.ForbidenCharacterInHeader, wholeInstruction, this.line);
//				if (in.charAt(i) == '\n')
//					instructionArea++;
//				if (in.charAt(i) == '(')
//					opened++;
//				if (in.charAt(i) == ')')
//					opened--;
//				if (opened < 0) {
//					throw new WrongConditionException(Error.InvalidParenthesis, wholeInstruction, this.line);
//				}
//				if (opened == 0) {
//					condition = new Statement(in.substring(0, i));
//					Matcher states = Patterns.states.matcher(in.substring(i + 1));
//					if (states.find()) {
//						this.area = instructionArea + ParseUtils.getLines(header) - ParseUtils.getLinesBNS(header); // zmiana  o kteórej by³o mowione wczesniej
//						this.beginOfStatements = this.line + this.area + ParseUtils.getLinesBNS(in.substring(i + 1));
//						this.content = states.group();
//						if(!ParseUtils.checkBetweenCondStates(in.substring(i + 1), this.content))
//							this.addError(Error.InvalidCondition);
//						return;
//					} else
//						throw new WrongBlockException(wholeInstruction, in.substring(i + 1), this.line, this.area);
//				}
//			}
//			throw new WrongConditionException(Error.InvalidCondition, wholeInstruction, this.line);
//		} else {
//			Matcher states = Patterns.states.matcher(in);
//			if (states.find()) {
//				this.area = 0;
//				this.beginOfStatements = this.line + this.area + ParseUtils.getLinesBNS(in);
//				this.content = states.group();
//			} else
//				throw new WrongBlockException(wholeInstruction, in.substring(i + 1), this.line, this.area);
//		}

}
