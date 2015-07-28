package Atoms;

public class Comment 
{
	private int startLine, endLine;
	private String content;
	private boolean error = false;
	public Comment(String currentComment, int line, int line2) {
		content = currentComment;
		startLine = line;
		endLine = line2;
	}
	public void addError()
	{
		error = true;
	}

}
