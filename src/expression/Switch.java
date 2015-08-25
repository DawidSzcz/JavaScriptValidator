package expression;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.InvalidExpression;
import exception.InvalidString;
import exception.WrongComplexException;
import parser.Patterns;
import validator.Context;

public class Switch extends ComplexExpression {
	public Switch(String statement, int currentLine, List<String> labels, String branch) {
		super(statement, currentLine);
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.SWITCH)).matcher(statement);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
	}

	@Override
	public String toString() 
	{
		return branch + "Switch";
	}

	@Override
	public boolean isValid() {
		super.isValid();
		try {
			if (condition != null)
				condition.isValid();
		} catch (InvalidExpression e) {
			this.addError(e.getError(), line + e.getLine());
			return false;
		}catch (InvalidString e) {
			addError(e.getError(), e.getLine());
			return false;
		}
		return true;
	}

}
