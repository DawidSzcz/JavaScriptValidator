package exception;

public class WrongIfException extends WrongComplexException {
	String error;
	String block;
	public WrongIfException(String error, String statement) {
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
