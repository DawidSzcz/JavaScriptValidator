package exception;

import enums.Error;

public class InvalidExpression extends JSValidatorException {
	public InvalidExpression(Error err, String block) {
		this.error = err;
		this.block = block;
	}
	@Override
	public Error getError() {
		return error;
	}

	@Override
	public String getStatement() {
		return block;
	}

}
