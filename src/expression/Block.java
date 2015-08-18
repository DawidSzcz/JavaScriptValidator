package expression;

import java.util.List;

import parser.ParseUtils;

public class Block extends ComplexExpression {

	public Block(String in, int currentLine, String branch) {
		super(in, currentLine);
		this.branch = branch;
		beginOfStatements = ParseUtils.getLinesBNS(in)-1;
		area = 0;
	}

	@Override
	public String toString() {
		return branch + "BLOCK ";
	}

	@Override
	public boolean isValid() {
		return true;
	}

}
