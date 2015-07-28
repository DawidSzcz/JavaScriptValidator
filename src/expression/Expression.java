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
	protected Map<String, StringContainer> strings;
	protected int formerLine;
	List<enums.Error> errors = new LinkedList<>();
	
	public Expression(String name, int currentLine, Map<String, StringContainer> strings) 
	{
		this.formerLine = currentLine;
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
//	public boolean match(String s)
//	{
//		s = ParseUtils.removeCommentsFromLine(s);
//		try{
//			s = ParseUtils.cleanLine(s);
//		}catch(IllegalStateException e){
//			return false;
//		}
//		return translateName().contains(s);
//	}
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
//	public int setLine(List<String> instructions) {
//		for(int i = formerLine; i < instructions.size(); i++)
//		{
//			try{
//				String line = ParseUtils.cleanLine(instructions.get(i));
//				Matcher m = Patterns.commentLine.matcher(line);
//				while(m.find())
//					line = line.replace(m.group(), "");
//				if(match(line))
//				{
//					this.line= i+1;
//					return this.line;
//				}
//
//			}catch(IllegalStateException e){
//				
//			}
//		}
//		this.line = -2;
//		return formerLine;
//	}
}
