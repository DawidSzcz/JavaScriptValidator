package exception;

import enums.Error;

public class WrongCatchException extends JSValidatorException {
	public WrongCatchException(Error error, String statement) {
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
