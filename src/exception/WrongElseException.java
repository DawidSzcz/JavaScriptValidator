package exception;

import enums.Error;

public class WrongElseException extends JSValidatorException {
	enums.Error error;
	String block;
	public WrongElseException(Error err, String state) {
		error = err;
		block = state;
	}
	@Override
	public Error getError() {
		return error;
	}

	@Override
	public String getStatement() {
		return block;
	}

}
