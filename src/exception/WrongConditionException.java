package exception;

import enums.Error;

public class WrongConditionException extends WrongComplexException {
	public final int line;
	public WrongConditionException(Error error, String statement, int line) {
		super(error, statement);
		this.line = line;
	}

}
