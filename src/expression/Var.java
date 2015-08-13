package expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import parser.ParseUtils;

public class Var extends SimpleExpression {
	List<Assignment> assignments = new ArrayList<Assignment>();

	public Var(String statement, int currentLine, Map<String, StringContainer> strings, String branch) {
		super(statement, currentLine, strings);
		this.branch = branch;
		statement = statement.replaceFirst(enums.Instruction.VAR.toString(), "");
		String[] variables = statement.split(",");
		for (String variable : variables) {
			try {
				assignments.add(new Assignment(variable, currentLine, strings, branch));
			} catch (IllegalStateException e) {
				errors.add(enums.Error.NullSteatment);
				assignments.add(new Assignment("null", currentLine, strings, branch));

			}
		}
	}

	@Override
	public String toString() {
		return "declaration of variables";
	}

}
