package expression;

import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import exception.InvalidExpression;
import parser.ParseUtils;
import parser.Patterns;
import validator.Context;


public class Assignment extends SimpleExpression {
	List<Statement> variables=new ArrayList<Statement>();
	Statement argument;

	public Assignment(String statement, int currentLine, String branch){
		super(statement, currentLine);
		this.branch = branch;
		if (statement.length()>0 && statement.substring(statement.length()-1).equals("=")){
			statement=statement+" ";
		}
		String side[] = statement.split(Patterns.assignDivisionS);
		if (side.length >= 2) {
			try{
				argument = new Statement(ParseUtils.cleanLine(side[side.length-1]));
			}catch(IllegalStateException e){
				errors.add(enums.Error.NullSteatment);
				argument = new Statement(ParseUtils.cleanLine("null"));
			}
			for (int i = side.length-2 ; i >= 0; i--) {
				try{
				variables.add(new Statement(ParseUtils.cleanLine(side[i])));
				Context.variables.add(side[i]);
				}
				catch(IllegalStateException e){
					errors.add(enums.Error.NullSteatment);
					variables.add(new Statement(ParseUtils.cleanLine("null")));
				}
			}
		}

		else
		{
			statement = ParseUtils.cleanLine(statement);
			this.addError(Error.WrongAssignment);
		}
	}

	public String toString() {
		return branch + "Assignment";
	}

	@Override
	public boolean isValid() {
		super.isPortOpen(argument,variables);
		for(Statement variable:variables){
			if(variable.getName().matches("\\s*"+operator.Patterns.number+"\\s*")){
				errors.add(enums.Error.ExpectedVariableNotNumber);
				return false;
			}
		}
		if (super.isValid()) {
			try {
				for (Statement vsriable : variables) {
					vsriable.isValid();
				}
				argument.isValid();
			} catch (InvalidExpression e) {
				this.addError(e.getError());
				return false;
			}
			return true;
		} else
			return false;
	}
}
