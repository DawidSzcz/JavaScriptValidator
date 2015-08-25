package expression;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import enums.Error;
import enums.Instruction;
import exception.InvalidExpression;
import exception.InvalidString;
import parser.Patterns;

public class If extends ComplexExpression{
	public If(String statement, int currentLine, String branch) {
		super(statement, currentLine);
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.IF)).matcher(statement);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
		this.branch = branch;
	}

	@Override
	public String toString() {
		return branch + "IF ";
	}
	protected Statement getCondition()
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
	@Override
	public boolean isValid() {
		super.isValid();
		try{
			if(condition != null)
				condition.isValid();
		}catch(InvalidExpression e)
		{
			this.addError(e.getError(), line + e.getLine());
			return false;
		}catch (InvalidString e) {
			addError(e.getError(), e.getLine());
			return false;
		}
		return true;
	}
}
