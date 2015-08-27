package expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.Statement;

import enums.Error;
import parser.Patterns;


public abstract class ComplexExpression extends Expression {

	protected List<Expression> statements = null;
	protected int beginOfStatements;
	protected Statement condition;

	protected List<String> labels = new LinkedList<>();
	public ComplexExpression(String in,  int currentLine, List<String> labels) {
		super(in, currentLine);
		
		this.labels.addAll(labels);
		
		Matcher matchCondition = Patterns.condition.matcher(in);
		if(matchCondition.find())
			condition = new Statement(matchCondition.group());
		else if(this instanceof Try || this instanceof Else || this instanceof Program || this instanceof Block)
				condition = null;
			else
				this.addError(Error.InvalidCondition, line);
		beginOfStatements = line + area;

	}

	@Override
	public HashMap<Integer, List<Error>> getAllErrors() {
		HashMap<Integer, List<Error>> hash = new HashMap<>();
		for(int line : super.getAllErrors().keySet())
		{
			hash.put(line, new LinkedList<>());
			hash.get(line).addAll(super.getAllErrors().get(line));
		}
		if(statements != null)
			for (Expression exp : statements) {
				for (Integer l : exp.getAllErrors().keySet()) {
					if (!hash.keySet().contains(l))
						hash.put(l, new LinkedList<>());
					hash.get(l).addAll(exp.getAllErrors().get(l));
				}
			}
		return hash;
	}
	public void insertBlock( List<Expression> block)
	{
		this.statements = block != null ? block : new LinkedList<Expression>();
	}
	@Override
	public void addtoInstructions(Map<Integer, List<Expression>> instructions) {
		super.addtoInstructions(instructions);
		if(statements != null)
			for (Expression exp : statements)
				exp.addtoInstructions(instructions);
	}
	public int nextLine()
	{
		return beginOfStatements;
	}
	@Override
	public boolean isValid()
	{
		if(statements == null)
		{
			this.addError(Error.InvalidBlock, beginOfStatements);
			return false;
		}
		return true;
	}
	public List<String> getLabels() {
		return labels;
	}
}
