package exception;

import enums.Error;

public class WrongBlockException extends WrongComplexException {
	public final int line;
	public final int area;
	public final int nextStatement;
	public final String content;
	public WrongBlockException(String statement, String content, int beginStatement, int area, int nextStatement) {
		super(Error.InvalidBlock, statement);
		this.line = beginStatement;
		this.area = area;
		this.nextStatement = nextStatement;
		this.content = content;
		
	}

}
