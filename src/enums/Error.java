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
	MissingIfBeforeElse("MissingIfBeforeElse"),
	InvalidOperator("Invalid operator"),
	InvalidFunction("Invalid function"),
	InvalExpresionInSqareBracket("Inval expresion in sqare bracket"),
	InvalExpresionInParenthesis("Inval Expresion In Parenthesis"), 
	InvalidElseName("Invalid Else Name"), 
	InvalidElseIf("Invalid If in ElseIf"), 
	InvalidForCondition("Invalid For Condition"),
	IvalidBeginning("Invalid beggining of complex statement"), 
	ForbidenCharacterInHeader("Forbiden character in header"), 
	InvalidCondition("Invalid Condition");
	
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
