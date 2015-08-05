package expression;

import java.util.HashMap;
import java.util.Map;

import Atoms.StringContainer;
import enums.Error;
import parser.ParseUtils;

public class InvalidComment extends Expression
{
	public InvalidComment(String name, Error error, int currentLine) {
		super(name, new HashMap<>());
		line = currentLine;
		area = ParseUtils.getLines(name);
		this.addError(error);
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
