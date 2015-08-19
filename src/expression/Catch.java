package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.Error;
import enums.Instruction;
import exception.InvalidExpression;
import parser.Patterns;
import simpleExpression.ExpresionCorrect;

public class Catch extends ComplexExpression {

	public Catch(String name, int currentLine, String branch){
		super(name, currentLine);

		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.CATCH)).matcher(name);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase);
		}
		this.branch = branch;
	}


	@Override
	public String toString() {
		return branch + "CATCH ";
	}

	@Override
	public boolean isValid() {
		try {
			ExpresionCorrect.declarationException(condition.getName());
		} catch (InvalidExpression e) {
			this.addError(e.getError());
		}
		return super.isValid();
	}

	public int size() {
		return statements.size() + 1;
	}

}
