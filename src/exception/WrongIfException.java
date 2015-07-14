package exception;
import enums.Error;

public class WrongIfException extends JSValidatorException {
	public WrongIfException(Error error, String statement) {
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
