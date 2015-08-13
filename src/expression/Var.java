package expression;

import java.util.Map;

import Atoms.StringContainer;

public class Var extends SimpleExpression{
	String[] variables;
	public Var(String statement, int currentLine, Map<String, StringContainer> strings, String branch) {
		super(statement, currentLine, strings);
		this.branch=branch;
		statement=statement.replaceFirst(enums.Instruction.VAR.toString(), "");
		variables=statement.split(",");
		
	}

	@Override
	public String toString() {
		return "declaration of variables";
	}

}
