package exception;

public abstract class WrongComplexException extends Exception {
	public abstract String getError();
	public abstract String getStatement();
}
