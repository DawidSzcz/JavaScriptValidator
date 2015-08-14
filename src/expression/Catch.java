package expression;

import java.util.List;
import java.util.Map;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import parser.ExpressionParser;

public class Catch extends ComplexExpression {

	public Catch(String name, int currentLine, String branch){
		super(name, currentLine);
		this.branch = branch;
	}


	@Override
	public String toString() {
		return branch + "CATCH";
	}

	@Override
	public boolean isValid() {
		try {
			condition.isValid();
		} catch (InvalidOperator e) {
			this.addError(Error.InvalidOperator);
		} catch (InvalidFunction e) {
			this.addError(Error.InvalidFunction);
		}
		return super.isValid();
	}

	public int size() {
		return statements.size() + 1;
	}

}
