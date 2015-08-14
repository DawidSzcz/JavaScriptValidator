package expression;

import java.util.List;

public class Block extends ComplexExpression {

	public Block(String in, int currentLine, String branch) {
		super(in, currentLine);
		this.branch = branch;
	}

	@Override
	public String toString() {
		return branch;
	}

	@Override
	public boolean isValid() {
		return true;
	}

}
