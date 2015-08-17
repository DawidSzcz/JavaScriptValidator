package expression;

import java.util.List;

import parser.ParseUtils;

public class Block extends ComplexExpression {

	public Block(String in, int currentLine, String branch) {
		super(in, currentLine);
		this.branch = branch;
		beginOfStatements = line + ParseUtils.getLinesBNS(in);
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
