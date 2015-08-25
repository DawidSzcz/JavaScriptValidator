package exception;

import enums.Error;

public class InvalidString extends JSValidatorException{
	int line;
	public InvalidString(Error err, String block, int line) {
		this.error = err;
		this.block = block;
		this.line = line;
	}
	@Override
	public Error getError() {
		return error;
	}

	@Override
	public String getStatement() {
		return block;
	}
	public int getLine() {
		return line;
	}

}
