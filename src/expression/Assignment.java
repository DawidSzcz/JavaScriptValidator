package expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongAssignmentException;
import parser.ParseUtils;
import parser.Patterns;

public class Assignment extends SimpeExpresion {
	List<Statement> variables;
	Statement argument;

	public Assignment(String statement, int currentLine, Map<String, StringContainer> strings)
			throws WrongAssignmentException {
		super(statement, currentLine, strings);
		statement = ParseUtils.cleanLine(statement);
		String side[] = statement.split(Patterns.assignDivisionS);
		if (side.length >= 2) {
			argument = new Statement(side[side.length - 1]);
			for (int i = side.length - 2; i >= 0; i--) {
				variables.add(new Statement(side[i]));
			}

		}

		else
			throw new WrongAssignmentException(Error.WrongAssignment, statement);
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if (index == 0)
			return this;
		else
			throw new IndexOutOfBoundsException();
	}

	public String toString() {
		return "Assignment";
	}

	@Override
	public boolean isValid() {
		if (super.isValid()) {
			try {
				for (Statement vsriable : variables) {
					vsriable.isValid();
				}
				argument.isValid();
			} catch (InvalidOperator e) {
				this.addError(e.getError());
				return false;
			} catch (InvalidFunction e) {
				this.addError(e.getError());
				return false;
			}
			return true;
		} else
			return false;
	}
}
