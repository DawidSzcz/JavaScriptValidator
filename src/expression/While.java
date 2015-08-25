package expression;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.InvalidExpression;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;

public class While extends ComplexExpression{
	public While(String statement, int currentLine, List<String> labels, String branch) 
	{
		super(statement, currentLine);
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.WHILE)).matcher(statement);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
		this.branch = branch;
		Matcher label = Patterns.label.matcher(statement);
		if(label.find())
			labels.add(ParseUtils.cleanLine(label.group().substring(0, label.group().length() -1)));
	}

	@Override
	public String toString() {
		return branch + "While ";
	}
	@Override
	public boolean isValid() {
		super.isValid();
		try{
			if(condition != null)
				condition.isValid();
			else 
				return false;
			return true;
		}catch(InvalidExpression e)
		{
			this.addError(e.getError(), line + e.getLine());
			return false;
		}
	}
}
