package expression;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xslf.model.geom.ExpressionParser;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongComplexException;
import parser.ParseUtils;
import parser.Patterns;
import parser.ParseUtils.Triple;

public abstract class ComplexExpression extends Expression {

	protected List<Expression> statements;
	protected int beginOfStatements;
	protected Statement  condition;
	protected String content;
	
	public ComplexExpression(String in, Instruction instruction, int currentLine,  Map<String, StringContainer> strings) throws WrongComplexException {
		super(in, strings);
		splitBlock(instruction, currentLine, in);

	}
	@Override
	public HashMap<Integer, List<Error>> getAllErrors() {
		HashMap<Integer, List<Error>> hash = new HashMap<>();
		if(!errors.isEmpty())
			hash.put(line, getErrors());
		for(Expression exp : statements)
		{
			for(Integer l: exp.getAllErrors().keySet())
			{
				if(!hash.keySet().contains(l))
					hash.put(l, exp.getAllErrors().get(l));
				else
					hash.get(l).addAll(exp.getAllErrors().get(l));
			}
		}
		return hash;
	}
	@Override
	public void addtoInstructions(Map<Integer, List<Expression>> instructions)
	{
		super.addtoInstructions(instructions);
		for(Expression exp : statements)
			exp.addtoInstructions(instructions);
	}
	public void splitBlock(Instruction instruction, int currentLine, String in) throws WrongComplexException 
	{
		String wholeInstruction = in;
		List<Character> forbiden = Arrays.asList('{', '}', ';');
		String header;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplex, instruction)).matcher(in);
		int opened = 1;
		int instructionArea = 0, lineBeforeStatement;
		if (checkBeginning.find()) {
			header = checkBeginning.group();
			in = in.replace(header, "");
			lineBeforeStatement = ParseUtils.getLines(header);
			this.line = currentLine + lineBeforeStatement;
		} else
			throw new WrongComplexException(Error.InvalidBeginning, wholeInstruction);

		if (!instruction.equals(Instruction.TRY) && !instruction.equals(Instruction.ELSE)) {
			for (int i = 0; i < in.length(); i++) {
				if (forbiden.contains(in.charAt(i)))
					throw new WrongComplexException(Error.ForbidenCharacterInHeader, wholeInstruction);
				if (in.charAt(i) == '\n')
					instructionArea++;
				if (in.charAt(i) == '(')
					opened++;
				if (in.charAt(i) == ')')
					opened--;
				if(opened < 0)
				{
					throw new WrongComplexException(Error.InvalidParenthesis, wholeInstruction);
				}
				if (opened == 0) 
				{
					condition = new Statement(in.substring(0, i));
					Matcher states = Patterns.states.matcher(in.substring(i + 1));
					if (states.find())
					{
						this.area = instructionArea;
						this.beginOfStatements = this.line + this.area + ParseUtils.getLinesBNS(in.substring(i + 1));
						this.content = states.group();
						return;
					}
					else
						throw new WrongComplexException(Error.InvalidBlock, wholeInstruction);
				}
			}
			throw new WrongComplexException(Error.InvalidCondition, wholeInstruction);
		} else {
			Matcher states = Patterns.states.matcher(in);
			if (states.find())
			{
				this.area= 1;
				this.beginOfStatements = this.line + this.area + ParseUtils.getLinesBNS(in);
				this.content = states.group();
			}
			else
				throw new WrongComplexException(Error.InvalidBlock, wholeInstruction);
		}

	}
	
}
