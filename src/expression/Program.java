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
		super(input, 1);
		statements = (new ExpressionParser(input)).parse();
	}

	@Override
	public String toString() {
		return "Program";
	}
	public boolean isValid() {
		return true;
	}
	public Map<Integer, List<Expression>> mapExpression()
	{
		Map<Integer, List<Expression>> instructions = new HashMap<Integer, List<Expression>>();
		for(Expression exp : statements)
			exp.addtoInstructions(instructions);
		return instructions;
	}

}
