package exception;

public class WrongAssignmentException extends JSValidatorException{
	enums.Error error;
	String block;
	public WrongAssignmentException(enums.Error error, String statement) {
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
