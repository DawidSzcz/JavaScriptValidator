package expression;

import java.util.ArrayList;
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
import validator.Context;

public class Function extends ComplexExpression {
	List<Statement>  arguments = new LinkedList<Statement>(); 
	List <String> args;
	String functionName;
	public Function(String statement, int currentLine, Map<String, StringContainer> strings, List<String> labels) 
	{
		super(statement, Instruction.FUNCITON, currentLine, strings, labels);
		for (String arg:args)
			arguments.add(new Statement(arg));
		functionName = getName(statement);
		if (Context.functions.containsKey(functionName)){
			Context.functions.get(functionName).add(args.size());
		}else{
			List<Integer> list = new ArrayList<Integer>();
			list.add(args.size());
			Context.functions.put(functionName,list);
		}
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
	public void splitBlock(Instruction instruction, int currentLine, String in, List<String> labels) throws WrongComplexException {
		String wholeInstruction = in;
		String header;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction)).matcher(in);
		int opened = 1;
		int instructionArea = 0, lineBeforeStatement;
		if (checkBeginning.find()) {
			header = checkBeginning.group();
			in = in.replace(header, "");
			lineBeforeStatement = ParseUtils.getLinesBNS(header);
			this.line = currentLine + lineBeforeStatement;
		} 		
		else
		{
			checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, instruction), Pattern.CASE_INSENSITIVE).matcher(in); 
			if (checkBeginning.find()) {
				this.addError(Error.RestrictedLowerCase);
				header = checkBeginning.group();
				in = in.replace(header, "");
				lineBeforeStatement = ParseUtils.getLinesBNS(header);
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
				args=makeArgs(in.substring(0, i));
				Matcher states = Patterns.states.matcher(in.substring(i + 1));
				if (states.find())
				{
					this.area = instructionArea + ParseUtils.getLines(header) - ParseUtils.getLinesBNS(header);
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
	private String getName(String statement){
		statement = statement.substring(0, statement.indexOf('('));
		String name = statement.replaceAll("function", "");
		name = parser.ParseUtils.cleanLine(name);
		return name;
	}
	public List<String> makeArgs(String in)
	{
		List<String> list = new LinkedList<>();
		String currentArg ="";
		int opened = 1;
		for (int i = 0; i < in.length(); i++) {
			if(opened == 1 && in.charAt(i)== ',')
			{
				list.add(currentArg);
				currentArg = "";
				continue;
			}
			if (in.charAt(i) == '(')
				opened++;
			if (in.charAt(i) == ')')
				opened--;
			currentArg += in.charAt(i);
		}
		list.add(currentArg);
		return list;
	}
}
