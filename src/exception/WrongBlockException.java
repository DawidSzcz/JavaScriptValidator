package exception;

import enums.Error;

public class WrongBlockException extends WrongComplexException {
	public final int line;
	public final int area;
	public final int nextStatement;
	public final String content;
	public WrongBlockException(String statement, String content, int beginStatement, int area) {
		super(Error.InvalidBlock, statement);
		this.line = beginStatement;
		this.area = area;
		this.nextStatement = area+beginStatement;
		this.content = content;
		
	}

}
