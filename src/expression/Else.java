package expression;

import java.util.List;

public class Else extends If
{
	List<Expression> elseStatements;
	Else(String name, If If, List<Expression> els)
	{
		super(name, If.getCondition(), If.getStatements());
		elseStatements = els;
	}
}
