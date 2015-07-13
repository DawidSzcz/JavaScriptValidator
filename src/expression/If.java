package expression;

import java.util.List;

public class If extends ComplexExpression{
	String condition;
	public If(String name, int line, String cond, List<Expression> stat) {
		super(name, line);
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
	protected String getName()
	{
		return name;
	}
}
