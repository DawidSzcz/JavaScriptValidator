package expression;

import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import exception.ExceptionContainer;
import exception.InvalidExpression;
import exception.InvalidString;
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
				addError(Error.NullSteatment, line);
				argument = new Statement(ParseUtils.cleanLine("null"));
			}
			for (int i = side.length-2 ; i >= 0; i--) {
				try{
				variables.add(new Statement(ParseUtils.cleanLine(side[i])));
				Context.variables.add(side[i]);
				}
				catch(IllegalStateException e){
					addError(Error.NullSteatment, line);
					variables.add(new Statement(ParseUtils.cleanLine("null")));
				}
			}
		}

		else
		{
			statement = ParseUtils.cleanLine(statement);
			addError(Error.WrongAssignment, line);
		}
	}

	public String toString() {
		return branch + "Assignment";
	}

	@Override
	public boolean isValid() {
		boolean valid = super.isValid();
		super.isPortOpen(argument,variables);
		for(Statement variable:variables){
			if(variable.getName().matches("\\s*"+simpleExpression.Patterns.number+"\\s*")){
				addError(Error.ExpectedVariableNotNumber, line);
				valid = false;
			}
			try {
				variable.isValid();
			} catch (ExceptionContainer ex) {
				for(InvalidExpression e : ex.getInlineExceptions())
					addError(e.getError(), line + e.getLine());
				for(InvalidString e : ex.getBeginningExceptions())
					addError(e.getError(), e.getLine());
				valid =  false;
			}
		}
		try{
			argument.isValid();
		}catch (ExceptionContainer ex) {
			for(InvalidExpression e : ex.getInlineExceptions())
				addError(e.getError(), line + e.getLine());
			for(InvalidString e : ex.getBeginningExceptions())
				addError(e.getError(), e.getLine());
			valid =  false;
		}
		return valid;
	}
}
