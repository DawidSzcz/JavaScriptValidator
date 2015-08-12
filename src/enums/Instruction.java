package enums;

public enum Instruction {
	
	TRY("try"),
	ELSE("else"),
	IF("if\\s*\\("),
	FOR("for\\s*\\("),
	SWITCH("switch\\s*\\("),
	WHILE("while\\s*\\("),
	CATCH("catch\\s*\\("),
	FUNCITON("function\\s+[_\\$a-zA-Z]+[_\\$\\w]*\\s*\\("),
	PROGRAM("");
	
	public final String content;
	Instruction(String str)
	{
		content = str;
	}
	public String toString()
	{
		return content;
	}
}
