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
	protected Map<String, StringContainer> strings;
	protected String branch = "";
	List<enums.Error> errors = new LinkedList<>();
	
	public Expression(String name, Map<String, StringContainer> strings) 
	{
		this.strings = strings;
		try{
			this.name = ParseUtils.cleanLine(name);
		}catch(IllegalStateException e){
			addError(enums.Error.UnparsedLine);
			this.name ="Unparsed";
		}
	}
	public abstract Expression get(int index) throws IndexOutOfBoundsException;

	public abstract String toString();
	public abstract boolean isValid();
	
	public List<enums.Error> getErrors()
	{
		return errors;
	}
	public boolean hasErrors()
	{
		return !errors.isEmpty();
	}
	public void addError(enums.Error err)
	{
		errors.add(err);
	}
	public void addtoInstructions(Map<Integer, List<Expression>> instructions, String branch)
	{
		this.isValid();
		this.branch = branch;
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
		HashMap<Integer, List<Error>> hash = new HashMap<>();
		if(!errors.isEmpty())
			hash.put(line, getErrors());
		return hash;
	}
	protected String translateName()
	{
		String wholeName = name;
		Matcher m = Patterns.stringID.matcher(wholeName);
		while(m.find())
		{
			String id = m.group();
			wholeName = wholeName.replace(id, strings.get(id).getString());
		}
		return wholeName;
	}
}
