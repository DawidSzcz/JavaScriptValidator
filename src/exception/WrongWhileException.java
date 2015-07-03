package exception;

public class WrongWhileException extends Exception {
	public WrongWhileException(String str) {
		System.err.println(str);
	}
}
