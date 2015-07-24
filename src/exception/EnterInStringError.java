package exception;

import enums.Error;

public class EnterInStringError extends JSValidatorException {
	public EnterInStringError(Error err, String block) {
	this.error = err;
	this.block = block;
}
	@Override
	public Error getError() {
		// TODO Auto-generated method stub
		return error;
	}

	@Override
	public String getStatement() {
		// TODO Auto-generated method stub
		return block;
	}

}
