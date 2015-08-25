package expression;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongComplexException;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.Patterns;
import validator.Context;

public class For extends ComplexExpression{
	Expression[] forConditions = new Expression[3];
	String[] conditions;
	public For(String statement, int currentLine, List<String> labels, String branch, ExpressionParser parser)
	{
		super(statement, currentLine);

		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.FOR)).matcher(statement);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
		this.branch = branch;
		Matcher label = Patterns.label.matcher(statement);
		if(label.find())
			labels.add(ParseUtils.cleanLine(label.group().substring(0, label.group().length() -1)));
		conditions = (condition.getName()+" ").split(";");
		if(conditions.length == 3)
		{
			for(int i = 0; i < 3; i++)
				if(!conditions[i].matches(Patterns.empty))
				{
					List<Expression> list = parser.parseExpressions(conditions[i], 0, new LinkedList<String>(), branch + "For ");
					if(list.size() != 1)
						this.addError(Error.InvalidArguments, line);
					else
						forConditions[i] = list.get(0);
				}
				else
					forConditions[i] = new EmptyExpression();			
		}
		else
		{
			this.addError(Error.WrongNumberOfArguments, line);
		}
			
	}


	@Override
	public String toString() {
		return branch + "For ";
	}

	@Override
	public boolean isValid() {
		super.isValid();
		boolean isValid = true;
		for(Expression e : forConditions)
			if(e == null || !e.isValid())
				isValid = false;
		return isValid;
	}
	@Override
	public boolean hasErrors(int i) {
		for(Expression e : forConditions)
			if(e == null || e.hasErrors(i - line))
				return true;
		return super.hasErrors(i);
	}
	@Override
	public boolean hasErrors() {
		for(Expression e : forConditions)
			if(e == null || e.hasErrors())
				return true;
		return super.hasErrors();
	}
	@Override
	public List<Error> getErrors(int i) {
		List<Error> errs= super.getErrors(i);
		for(Expression e : forConditions)
		{
			if(e != null)
				errs.addAll(e.getErrors(i - line));
		}
		return errs;
	}
}