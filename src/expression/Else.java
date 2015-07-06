package expression;

import java.util.List;

public class Else extends If
{
	List<Expression> elseStatements;
	Else(If If, List<Expression> els)
	{
		super(If);
		elseStatements = els;
	}
}
