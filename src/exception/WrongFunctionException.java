package exception;

import enums.Error;

public class WrongFunctionException extends JSValidatorException {
	public WrongFunctionException(Error error, String statement) {
		this.error = error;
		this.block = statement;

	}

	@Override
	public Error getError() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatement() {
		// TODO Auto-generated method stub
		return null;
	}

}
