package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import enums.Error;
import enums.Instruction;
import exception.ExceptionContainer;
import exception.InvalidExpression;
import exception.InvalidString;
import parser.ParseUtils;
import parser.Patterns;

public class Case extends SimpleExpression {

	Statement variant;
	public Case(String name, int currentLine, String branch) {
		super(name, currentLine);
		this.branch = branch;
		Matcher match = Patterns.Case.matcher(name);
		match.find();
		variant = new Statement(match.group().substring(0, match.group().length()-1));
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.CASE)).matcher(name);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
	}

	@Override
	public String toString() {
		return "Case ";
	}
	@Override
	public boolean isValid() {
		boolean valid = true;
		try{
			variant.isValid();
		}catch(ExceptionContainer ex){
			for(InvalidExpression e : ex.getInlineExceptions())
				addError(e.getError(), line + e.getLine());
			for(InvalidString e : ex.getBeginningExceptions())
				addError(e.getError(), e.getLine());
			valid =  false;
		}
		return valid;
			
	}

}
