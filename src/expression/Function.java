package expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongComplexException;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;

public class Function extends ComplexExpression {
	List<Statement>  arguments = new LinkedList(); 
	List <String> args;
	public Function(String statement, int currentLine, Map<String, StringContainer> strings) 
	{
		super(statement, Instruction.FUNCITON, currentLine, strings);
		for (String arg:args)
			arguments.add(new Statement(arg));		
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index -1);
	}
	@Override
	public String toString() {
		return branch + "Function";
	}
	@Override
	public boolean isValid() 
	{
		for(Statement condtioniterator:arguments){
			try {
				condtioniterator.isValid();
			} catch (InvalidOperator e) {
				return false;
			} catch (InvalidFunction e) {
				return false;
			}
		}
		return true;
	}
	@Override
	public void splitBlock(Instruction instruction, int currentLine, String in) throws WrongComplexException {
		String wholeInstruction = in;
		String header;
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
		for (int i = 0; i < in.length(); i++) {
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
				args=Arrays.asList(in.substring(0, i));
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

	}
}
