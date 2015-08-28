package expression;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.ExceptionContainer;
import exception.InvalidExpression;
import exception.InvalidString;
import exception.WrongComplexException;
import parser.Patterns;
import validator.Context;

public class Switch extends ComplexExpression {
	public Switch(String statement, int currentLine, List<String> labels, String branch) {
		super(statement, currentLine, labels);
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.SWITCH)).matcher(statement);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
	}

	@Override
	public String toString() 
	{
		return branch + "Switch ";
	}

	@Override
	public boolean isValid() {
		boolean valid = super.isValid();
		try {
			if (condition != null)
				condition.isValid();
		} catch (ExceptionContainer ex) {
			for(InvalidExpression e : ex.getInlineExceptions())
				addError(e.getError(), line + e.getLine());
			for(InvalidString e : ex.getBeginningExceptions())
				addError(e.getError(), e.getLine());
			valid =  false;
		}
		return valid;
	}

}
