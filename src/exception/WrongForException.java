package exception;

public class WrongForException extends WrongComplexException {
	String error;
	String block;
	public WrongForException(String error, String statement) {
		this.error = error;
		this.block = statement;
	}
	public String getError()
	{
		return error;
	}
	public String getStatement()
	{
		return block;
	}
}