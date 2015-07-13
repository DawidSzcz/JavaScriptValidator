package expression;

import java.util.List;

public class While extends ComplexExpression{
	String  condition;
	public While(String name, int line, String cond, List<Expression> stats) {
		super(name, line);
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
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
