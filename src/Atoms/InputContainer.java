package Atoms;

public class InputContainer extends StringContainer{
	private int line = 1;
	public int getLine(){
		return line;
	}
	public InputContainer(String string) {
		super(string);
	}
	public void addline() {
		line++;
		// TODO Auto-generated method stub
	}

}
