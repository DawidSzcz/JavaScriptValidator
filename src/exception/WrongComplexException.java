package exception;

public class WrongComplexException extends JSValidatorException{
	public 	 WrongComplexException(enums.Error error, String statement) {
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