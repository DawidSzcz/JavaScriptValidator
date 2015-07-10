package expression;

import java.util.List;

public class Function extends Expression {
	public Function(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	List<Expression> aguments;
	List<Expression> statements;
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
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
