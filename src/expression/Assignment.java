package expression;

import java.util.ArrayList;
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
	List<Statement> arguments=new ArrayList<Statement>();
	Statement variable;

	public Assignment(String statement, int currentLine, Map<String, StringContainer> strings)
			throws WrongAssignmentException {
		super(statement, currentLine, strings);
		if (statement.substring(statement.length()-1).equals("=")){
			statement=statement+" ";
		}
		String side[] = statement.split(Patterns.assignDivisionS);
		if (side.length >= 2) {
			side[0]=side[0].replace("var", "");
			try{
				variable = new Statement(ParseUtils.cleanLine(side[0]));
			}catch(IllegalStateException e){
				errors.add(enums.Error.NullSteatment);
				variable = new Statement(ParseUtils.cleanLine("null"));
			}
			for (int i = side.length-1 ; i > 0; i--) {
				try{
				arguments.add(new Statement(ParseUtils.cleanLine(side[i])));
				}
				catch(IllegalStateException e){
					errors.add(enums.Error.NullSteatment);
					arguments.add(new Statement(ParseUtils.cleanLine("null")));
				}
			}
		}

		else
		{

			statement = ParseUtils.cleanLine(statement);
			throw new WrongAssignmentException(Error.WrongAssignment, statement);
		}
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
		super.isPortOpen(variable,arguments);
		if (super.isValid()) {
			try {
				for (Statement vsriable : arguments) {
					vsriable.isValid();
				}
				variable.isValid();
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
