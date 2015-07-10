package expression;

import java.util.List;

public class If extends Expression{
	String condition;
	List<Expression> statements;
	public If(String name, String cond, List<Expression> stat) {
		super(name);
		condition = cond;
		statements = stat;
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
		return "IF ";
	}
	protected String getCondition()
	{
		return condition;
	}
	protected List<Expression> getStatements()
	{
		return statements;
	}
	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}
}
