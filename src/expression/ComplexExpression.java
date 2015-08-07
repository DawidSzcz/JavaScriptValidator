package expression;

import java.util.Arrays;
import java.util.HashMap;
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

	protected List<Expression> statements;
	protected int beginOfStatements;
	protected Statement condition;
	protected String content;

	public ComplexExpression(String in, Instruction instruction, int currentLine,	Map<String, StringContainer> strings) {
		super(in, strings);
		try {
			splitBlock(instruction, currentLine, in);
		}catch (WrongBlockException e) {
			this.addError(e.getError());
			this.content = e.content;
			this.beginOfStatements = e.nextStatement;
			this.line = e.line;
			this.area = e.area;
		} 
		catch (WrongConditionException e) {
			this.line = e.line;
			this.content = null;
			this.condition = null;
			this.addError(e.getError());
		}
		catch(WrongComplexException e)
		{
			System.err.println("URGENT URGENT URGENT !");
		}
		//Do zmiany
		if(content != null && instruction !=  Instruction.PROGRAM)
			this.statements = Context.expressionParser.parseExpressions(content, beginOfStatements);

	}

	@Override
	public HashMap<Integer, List<Error>> getAllErrors() {
		HashMap<Integer, List<Error>> hash = new HashMap<>();
		if (!errors.isEmpty())
			hash.put(line, getErrors());
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

	@Override
	public void addtoInstructions(Map<Integer, List<Expression>> instructions, String branch) {
		super.addtoInstructions(instructions, branch);
		if(statements != null)
			for (Expression exp : statements)
				exp.addtoInstructions(instructions, this.toString()+ " ");
	}

	public void splitBlock(Instruction instruction, int currentLine, String in) throws WrongComplexException {
		String wholeInstruction = in;
		List<Character> forbidden = Arrays.asList('{', '}', ';');
		String header;
		int i = 0;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction)).matcher(in);
		int opened = 1;
		int instructionArea = 0, lineBeforeStatement;
		if (checkBeginning.find()) {
			header = checkBeginning.group();
			in = in.replace(header, "");
			lineBeforeStatement = ParseUtils.getLines(header);
			this.line = currentLine + lineBeforeStatement;
		} 
		else
		{
			checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction), Pattern.CASE_INSENSITIVE).matcher(in); 
			if (checkBeginning.find()) {
				this.addError(Error.RestrictedLowerCase);
				header = checkBeginning.group();
				in = in.replace(header, "");
				lineBeforeStatement = ParseUtils.getLines(header);
				this.line = currentLine + lineBeforeStatement;
			} 
			else
				throw new WrongComplexException(Error.InvalidBeginning, wholeInstruction);
		}

		if (!instruction.equals(Instruction.TRY) && !instruction.equals(Instruction.ELSE)) {
			for (i = 0; i < in.length(); i++) {
				if (forbidden.contains(in.charAt(i)))
					throw new WrongConditionException(Error.ForbidenCharacterInHeader, wholeInstruction, this.line);
				if (in.charAt(i) == '\n')
					instructionArea++;
				if (in.charAt(i) == '(')
					opened++;
				if (in.charAt(i) == ')')
					opened--;
				if (opened < 0) {
					throw new WrongConditionException(Error.InvalidParenthesis, wholeInstruction, this.line);
				}
				if (opened == 0) {
					condition = new Statement(in.substring(0, i));
					Matcher states = Patterns.states.matcher(in.substring(i + 1));
					if (states.find()) {
						this.area = instructionArea;
						this.beginOfStatements = this.line + this.area + ParseUtils.getLinesBNS(in.substring(i + 1));
						this.content = states.group();
						if(!ParseUtils.checkBetweenCondStates(in.substring(i + 1), this.content))
							this.addError(Error.InvalidCondition);
						return;
					} else
						throw new WrongBlockException(wholeInstruction, in.substring(i + 1), this.line, this.area);
				}
			}
			throw new WrongConditionException(Error.InvalidCondition, wholeInstruction, this.line);
		} else {
			Matcher states = Patterns.states.matcher(in);
			if (states.find()) {
				this.area = 1;
				this.beginOfStatements = this.line + this.area + ParseUtils.getLinesBNS(in);
				this.content = states.group();
			} else
				throw new WrongBlockException(wholeInstruction, in.substring(i + 1), this.line, this.area);
		}

	}

}
