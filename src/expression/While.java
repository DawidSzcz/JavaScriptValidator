package expression;

import java.util.List;

public class While extends Expression{
	String  condition;
	List<Expression> statements;
	public While(String cond, List<Expression> stats) {
		condition = cond;
		statements = stats;
	}
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		if(index == 0)
			return this;
		else
			return statements.get(index - 1);
	}
	@Override
	public String toString() {
		return "While";
	}
}
