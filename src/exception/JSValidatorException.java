package exception;

public abstract class JSValidatorException extends Exception {
	enums.Error error;
	String block;
	public abstract enums.Error getError();
	public abstract String getStatement();

}
