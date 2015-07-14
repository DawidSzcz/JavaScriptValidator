package exception;

import enums.Error;

public class WrongWhileException extends JSValidatorException {
	public WrongWhileException(Error error, String statement) {
		this.error = error;
		this.block = statement;
	}
	public Error getError()
	{
		return error;
	}
	public String getStatement()
	{
		return block;
	}
}
