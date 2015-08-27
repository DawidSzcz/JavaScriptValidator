package expression;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.Error;
import enums.Instruction;
import exception.InvalidExpression;
import parser.Patterns;
import simpleExpression.ExpresionCorrect;

public class Catch extends ComplexExpression {

	public Catch(String name, int currentLine, List<String> labels, String branch){
		super(name, currentLine, labels);

		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.CATCH)).matcher(name);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
		this.branch = branch;
	}


	@Override
	public String toString() {
		return branch + "CATCH ";
	}

	@Override
	public boolean isValid() {
		boolean valid = super.isValid();
		try {
			ExpresionCorrect.declarationException(condition.getName());
		} catch (InvalidExpression e) {
			this.addError(e.getError(), line + e.getLine());
			valid = false;
		}
		return valid;
	}

	public int size() {
		return statements.size() + 1;
	}

}
