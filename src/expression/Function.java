package expression;

import java.util.HashMap;
import java.util.List;

import enums.Error;

public class Function extends ComplexExpression {
	public Function(String name, int line) {
		super(name, line);
		// TODO Auto-generated constructor stub
	}
	List<Expression> aguments;
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index -1);
	}
	@Override
	public String toString() {
		return "Function ";
	}
}
