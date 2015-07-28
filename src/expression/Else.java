package expression;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongComplexException;
import exception.WrongElseException;
import exception.WrongIfException;
import exception.WrongTryException;
import parser.ExpressionParser;
import parser.ParseUtils;
import parser.ParseUtils.Triple;
import parser.Patterns;

public class Else extends If
{
	List<Expression> elseStatements;
	String elseName;
	int elseLine;
	public Else(String statement, int currentLine, If If, Map<String, StringContainer> strings, ExpressionParser expressionParser) throws WrongIfException, IOException, WrongElseException 
	{
		super((If).getName(), currentLine, (If).getCondition(), strings, (If).getStatements());
		line = If.line;
		try{
			Triple divided = ParseUtils.splitBlock(Instruction.ELSE, statement);
			elseLine = currentLine + divided.lines;
			elseName = divided.header;
			
			elseStatements = expressionParser.parseExpressions(divided.statements, currentLine + divided.lineBeforeStatement);
		}catch(WrongComplexException e)
		{
			this.addError(e.getError());
			throw new WrongElseException(e.getError(), e.getStatement());
		}
	}
	public String toString()
	{
		return "IFElse";
	}
	@Override
	public Expression get(int i) throws IndexOutOfBoundsException
	{
		if(i <= this.statements.size())
			return super.get(i);
		else if(i == this.statements.size() + 1)
				return new NullExpression(elseName, -1, strings);
			else
				return elseStatements.get(i - statements.size() - 2);
	}
//	@Override
//	public boolean match(String s) {
//		s = ParseUtils.removeCommentsFromLine(s);
//		try{
//			s = ParseUtils.cleanLine(s);
//		}catch(IllegalStateException e){
//			return false;
//		}
//		return (super.match(s)|| elseName.contains(s));
//	}
	@Override
	public HashMap<Integer, List<Error>> getAllErrors() {
		HashMap<Integer, List<Error>> hash = super.getAllErrors();
		for(Expression exp : elseStatements)
		{
			for(Integer line: exp.getAllErrors().keySet())
			{
				if(!hash.keySet().contains(line))
					hash.put(line, exp.getAllErrors().get(line));
				else
					hash.get(line).addAll(exp.getAllErrors().get(line));
			}
		}
		return hash;
	}
	@Override
	public boolean isValid() {
		if(!super.isValid())
			return false;
		for(Expression exp: statements)
			if(!exp.isValid())
				return false;
		return true;
	}
//	@Override
//	public int setLine(List<String> instructions) {
//		for(int i = formerLine +1; i < instructions.size(); i++)
//		{
//			String line = instructions.get(i);
//			line = ParseUtils.removeCommentsFromLine(line);
//			if(name.contains(line))
//			{
//				this.elseLine= i+1;
//				return elseLine;
//			}
//		}
//		this.elseLine = -2;
//		return formerLine;
//	}
}