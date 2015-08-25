package expression;

import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import exception.InvalidExpression;

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
		if (super.isValid()) {
			try {
				invocation.isValid();
			} catch (InvalidExpression e) {
				this.addError(e.getError(), e.getLine() + line);
				return false;
			}
			return true;
		} else
			return false;
	}
}
