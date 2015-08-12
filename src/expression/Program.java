package expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enums.Instruction;
import exception.WrongComplexException;
import parser.ExpressionParser;
import validator.Context;

public class Program extends ComplexExpression 
{
	public Program(String input) throws WrongComplexException {
		super(input, Instruction.PROGRAM, 1, null, null, "");
		statements = Context.expressionParser.parse();
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		return statements.get(index);
	}

	@Override
	public String toString() {
		return "Program";
	}
	public boolean isValid() {
		return true;
	}
	@Override
	public void splitBlock(Instruction instruction, int currentLine, String in, List<String> str) throws WrongComplexException {
		line = currentLine;
		this.content = in;
	}
	public Map<Integer, List<Expression>> mapExpression()
	{
		Map<Integer, List<Expression>> instructions = new HashMap<Integer, List<Expression>>();
		for(Expression exp : statements)
			exp.addtoInstructions(instructions, "");
		return instructions;
	}

}
