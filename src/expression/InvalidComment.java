package expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Atoms.InputContainer;
import Atoms.StringContainer;
import enums.Error;
import parser.ParseUtils;

public class InvalidComment extends Expression
{
	public InvalidComment(String program, List<Error> errors, int line) {
		super("NVM", line);
		area = ParseUtils.getLines(program) - line +1;
		for(Error e : errors)
			this.addError(e, line);
	}

	@Override
	public String toString() {
		return branch + "Invalid Comment";
	}
	@Override
	public boolean isValid() {
		return false;
	}

}
