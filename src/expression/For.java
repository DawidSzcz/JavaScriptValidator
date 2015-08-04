package expression;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongComplexException;
import exception.WrongForException;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;

public class For extends ComplexExpression{
	Expression[] forConditions = new Expression[3];
	String[] conditions;
	public For(String statement, int currentLine, Map<String, StringContainer> strings, ExpressionParser expressionParser)
	{
		super(statement, Instruction.FOR, currentLine, strings);
		if(conditions.length == 3)
		{
			for(int i = 0; i < 3; i++)
				if(!conditions[i].matches(Patterns.empty))
				{
					List<Expression> list = expressionParser.parseExpressions(conditions[i], 0);
					if(list.size() != 1)
						this.addError(Error.InvalidArguments);
					else
						forConditions[i] = list.get(0);
				}
				else
					forConditions[i] = new EmptyExpression();			
		}
		else
		{
			this.addError(Error.WrongNumberOfArguments);
		}
		//Do zmiany
		if(content != null)
			this.statements = expressionParser.parseExpressions(content, beginOfStatements);
			
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index - 1);
	}

	@Override
	public String toString() {
		return "For";
	}

	@Override
	public boolean isValid() {
	boolean isValid = true;
	for(Expression e : forConditions)
		if(!e.isValid())
			isValid = false;
	return isValid;
	}
	@Override
	public boolean hasErrors() {
		for(Expression e : forConditions)
			if(e.hasErrors())
				return true;
		return false;
	}
	@Override
	public List<Error> getErrors() {
		List<Error> errs= super.getErrors();
		for(Expression e : forConditions)
			errs.addAll(e.getErrors());
		return errs;
	}
	@Override
	public void splitBlock(Instruction instruction, int currentLine, String in) throws WrongComplexException {
		String wholeInstruction = in;
		List<Character> forbiden = Arrays.asList('{', '}');
		String header;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction)).matcher(in);
		int opened = 1;
		int instructionArea = 0, lineBeforeStatement;
		if (checkBeginning.find()) {
			header = checkBeginning.group();
			in = in.replace(header, "");
			lineBeforeStatement = ParseUtils.getLines(header);
			this.line = currentLine + lineBeforeStatement;
		} else
			throw new WrongComplexException(Error.InvalidBeginning, wholeInstruction);

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
				conditions = (in.substring(0, i)+" ").split(";"); //bez slacji for (i=cos ; i<cos;) nie przechodzi 
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