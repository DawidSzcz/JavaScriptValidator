package exception;
import enums.Error;

public class WrongIfException extends WrongComplexException {
	Error error;
	String block;
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
