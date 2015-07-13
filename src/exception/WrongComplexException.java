package exception;

public abstract class WrongComplexException extends Exception {
	public abstract enums.Error getError();
	public abstract String getStatement();
}
