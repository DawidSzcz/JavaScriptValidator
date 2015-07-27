package exception;

import enums.Error;
import expression.ComplexExpression;
import expression.Expression;

public class WrongTryException extends JSValidatorException {
	public WrongTryException(Error error, String statement) {
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
