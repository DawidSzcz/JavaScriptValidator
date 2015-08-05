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
		super("NVM", new HashMap<>());
		this.line = line;
		area = ParseUtils.getLines(program) - line +1;
		for(Error e : errors)
			this.addError(e);
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		return "Invalid Comment";
	}
	@Override
	public boolean isValid() {
		return false;
	}

}
