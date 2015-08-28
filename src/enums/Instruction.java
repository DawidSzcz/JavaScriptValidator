package enums;

public enum Instruction {
	
	TRY("try"),
	ELSE("else"),
	IF("if\\s*\\("),
	SWITCH("switch\\s*\\("),
	FOR("([\\w$_]+:\\s*)?for\\s*\\("),
	WHILE("([\\w$_]+:\\s*)?while\\s*\\("),
	CATCH("catch\\s*\\("),
	FUNCTION("function\\s+[\\w$_]+\\s*\\("),
	VAR("(^|(?<=\\s))var(?=\\W)"),
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
