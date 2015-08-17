package expression;

import exception.InvalidExpression;

public class Catch extends ComplexExpression {

	public Catch(String name, int currentLine, String branch){
		super(name, currentLine);
		this.branch = branch;
	}


	@Override
	public String toString() {
		return branch + "CATCH ";
	}

	@Override
	public boolean isValid() {
		try {
			condition.isValid();
		} catch (InvalidExpression e) {
			this.addError(e.getError());
		}
		return super.isValid();
	}

	public int size() {
		return statements.size() + 1;
	}

}
