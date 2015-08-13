package enums;

public enum Instruction {
	
	TRY("^\\s*try\\W"),
	ELSE("^\\s*else\\W"),
	IF("^\\s*if\\s*\\("),
	SWITCH("^\\s*([\\w$_]+:\\s*)?switch\\s*\\("),
	FOR("^\\s*([\\w$_]+:\\s*)?for\\s*\\("),
	WHILE("^\\s*([\\w$_]+:\\s*)?while\\s*\\("),
	CATCH("^\\s*catch\\s*\\("),
	FUNCITON("^\\s*function\\s+[_\\$a-zA-Z]+[_\\$\\w]*\\s*\\("),
	VAR("^\\s*var\\W"),
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
