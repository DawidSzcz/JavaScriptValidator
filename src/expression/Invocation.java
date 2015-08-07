package expression;

import java.util.Map;

import Atoms.Statement;
import Atoms.StringContainer;
import exception.InvalidFunction;
import exception.InvalidOperator;

public class Invocation extends SimpeExpresion {
	Statement invocation;

	public Invocation(String str, int currentLine, Map<String, StringContainer> strings) {
		super(str, currentLine, strings);
		invocation = new Statement(str);
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if (index == 0)
			return this;
		else
			throw new IndexOutOfBoundsException();
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
