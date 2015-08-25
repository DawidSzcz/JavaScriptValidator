package expression;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Error;
import parser.ParseUtils;
import parser.Patterns;

public abstract class Expression {
	protected String name;
	protected int line;
	protected int area;
	protected String branch = "";
	private HashMap<Integer, List<enums.Error>> errors = new HashMap<>();
	
	public Expression(String name, int currentLine) 
	{
		try{
			this.name = ParseUtils.cleanLine(name);
		}catch(IllegalStateException e){
			addError(enums.Error.UnparsedLine, currentLine);
			this.name ="Unparsed";
		}
		this.line = currentLine + ParseUtils.getLinesBNS(name);
		this.area = ParseUtils.getArea(name);
	}
	
	public abstract String toString();
	public abstract boolean isValid();
	
	public List<enums.Error> getErrors(int i)
	{
		return errors.get(i) != null ? errors.get(i) : new LinkedList<enums.Error>();
	}
	public boolean hasErrors(int i)
	{
		return errors.get(i) != null;
	}
	public void addError(enums.Error err, int i)
	{
		if(errors.get(i) == null) 
			errors.put(i, new LinkedList<>());
		errors.get(i).add(err);
	}
	public void addtoInstructions(Map<Integer, List<Expression>> instructions)
	{
		this.isValid();
		for(int i = line; i <=line +area; i++)
			try{
				instructions.get(i).add(this);
			}catch(NullPointerException e){
				instructions.put(i, new LinkedList<Expression>());
				instructions.get(i).add(this);
			}
	}
	public int getLine()
	{
		return line;
	}
	public HashMap<Integer, List<Error>> getAllErrors() {
		return errors;
	}

	public String getBranch() {
		return toString();
	}
}
