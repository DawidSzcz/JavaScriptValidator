package enums;

public enum Error {
	InvalidBlock("InvalidBlock"),
	InvalidArguments("InvalidArguments"),
	UnexpectedOpeningBracket("Unexpected opening bracket"),
	UnexpectedClosingBracket("Unexpected closing bracket"),	
	UnparsedLine("Unparsed line"),
	MissingOpenningBracket("Missing opening bracket"),
	WrongNumberOfArguments("Wrong number of arguments"),
	WrongAssignment("Wrong assignment"),
	MissingIfBeforeElse("MissingIfBeforeElse");
	
	public final String content;
	Error(String str)
	{
		content = str;
	}
	public String toString()
	{
		return content;
	}
	
}
