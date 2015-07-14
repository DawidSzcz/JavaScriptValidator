package exception;

import enums.Error;

public class InvalidOperator extends JSValidatorException {
	public InvalidOperator(Error err, String block) {
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
