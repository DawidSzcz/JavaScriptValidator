package expression;

import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import exception.ExceptionContainer;
import exception.InvalidExpression;
import exception.InvalidString;

public class Invocation extends SimpleExpression {
	Statement invocation;

	public Invocation(String str, int currentLine, String branch) {
		super(str, currentLine);
		this.branch = branch;
		invocation = new Statement(str);
	}

	@Override
	public String toString() {
		return branch + "Invocation";
	}

	@Override
	public boolean isValid() {
		boolean valid = super.isValid();
		try {
			invocation.isValid();
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
