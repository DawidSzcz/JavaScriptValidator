package exception;

public class WrongWhileException extends Exception {
	String error;
	String block;
	public WrongWhileException(String error, String statement) {
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
