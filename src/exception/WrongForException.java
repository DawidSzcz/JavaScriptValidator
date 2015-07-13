package exception;

public class WrongForException extends WrongComplexException {
	enums.Error error;
	String block;
	public WrongForException(enums.Error error, String statement) {
		this.error = error;
		this.block = statement;
	}
	public enums.Error getError()
	{
		return error;
	}
	public String getStatement()
	{
		return block;
	}
}