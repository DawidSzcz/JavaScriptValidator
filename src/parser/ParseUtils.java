
package parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Comment;
import Atoms.StringContainer;
import enums.Error;
import enums.Instruction;
import exception.WrongComplexException;
import javafx.util.Pair;

public class ParseUtils {
	private static List<Character> allowedCharacters = Arrays.asList('\'', '"', 'f', 'n', '\\', 'r', 't', 'b');
	public static class Triple{
		public final String inputProgram;
		public final Map<String, Comment> comments;
		public final Map<String, StringContainer> strings;
		public Triple(String in, Map<String, Comment> com, Map<String, StringContainer> str)
		{
			inputProgram = in;
			comments = com;
			strings = str;
		}
	}
	public static String cleanLine(String statement) throws IllegalStateException
	{
		statement = statement.replace("\\s+", " ");
		Matcher matcher = Patterns.escapeWhiteSpace.matcher(statement);
		if(!matcher.find())
			throw new IllegalStateException();
		return matcher.group();
	}
	public static int getLine(List<String> instructions, String group) {
		group = cleanLine(group);
		for(int i = 0; i < instructions.size(); i++)
			try{
				if(group.contains(cleanLine(instructions.get(i))))
					return i + 1;
			}catch(IllegalStateException e){}
		return -2;
	}
	public static String uniqueId(String in) {
		Random rand = new Random();
		long x = rand.nextLong();
		String randomString = String.valueOf(x > 0 ? x : -x);
		while (in.contains(randomString))
		{
			x = rand.nextLong();
			randomString = String.valueOf(x > 0 ? x : -x);
		}
		return randomString;
	}
//	public static Pair<String, Map<String,StringContainer>> takeOutStrings(String javaScriptText, Map<String, StringContainer> stringMap) {
//		StringContainer stringInTexst= new StringContainer ("");
//		Boolean isInString = false;
//		char doubleQuotes='"';
//		char quotes= '\'';
//		for (int iterator = 0; iterator < javaScriptText.length(); iterator++) {
//			if (javaScriptText.charAt(iterator) == doubleQuotes || javaScriptText.charAt(iterator) == quotes) {
//				if (javaScriptText.charAt(iterator) == '"'){
//					quotes='"';
//				}
//				if (javaScriptText.charAt(iterator) == '\''){
//					doubleQuotes='\'';
//				}
//				if (!isInString) {
//					isInString = true;
//				} else {
//					//isInString = false;
//					stringInTexst.string+=javaScriptText.charAt(iterator);
//					String uniqueId= ParseUtils.uniqueId(javaScriptText);
//					stringMap.put("StringID"+uniqueId,stringInTexst);
//					javaScriptText=javaScriptText.replace(stringInTexst.string, "StringID" +uniqueId);
//					break;
//				}
//			}
//			if (isInString) {
//				if (javaScriptText.charAt(iterator)!='\n'){
//					stringInTexst.string+=javaScriptText.charAt(iterator);
//				}else {
//
//					stringInTexst.error=Error.InvalidString;
//				}
//			}
//		}
//		stringInTexst.error=Error.InvalidString;
//
//		return  new Pair<String, Map<String,StringContainer>>(javaScriptText, stringMap);
//	}
	
	public static ParseUtils.Triple removeStrAndCom(String jSText) 
	{
		boolean inlineComment =false, starComment = false, string= false;
		char stringDelimiter = '"';
		int line = 0, startLine = 0;
		List<Error> errors = new LinkedList<Error>();
		String finalString = "", currentString = "";
		HashMap<String, Comment> comments = new HashMap<String, Comment>();
		HashMap<String, StringContainer> strings = new HashMap<String, StringContainer>();
		for(int i = 0; i< jSText.length(); i++)
		{
			char c = jSText.charAt(i);
			if(!inlineComment && !starComment && !string)
			{
				if(c == '\n')
					line++;
				if(c == '/' && i != jSText.length() -1&& jSText.charAt(i+1) == '/')
				{
					inlineComment = true;
					currentString = "//";
					i++;
					continue;
				}
				if(c == '/' && i != jSText.length() -1 && jSText.charAt(i+1) == '*')
				{
					starComment = true;
					currentString = "/*";
					startLine = line;
					i++;
					continue;
				}
				if(c == '"' || c == '\'')
				{
					string = true;
					currentString = ""+c;
					stringDelimiter = c;
					continue;
				}
				finalString += c;
			}
			else
			{
				if(inlineComment && (c == '\n' || i == jSText.length() -1))
				{
					inlineComment = false;
					//String uniqueId = "CommentID"+ParseUtils.uniqueId(finalString + jSText);
					//finalString += uniqueId;
					//comments.put(uniqueId, new Comment(currentString + c, line, line));
					line++;
					currentString = "";
					continue;
				}
				if(starComment && c == '*' && jSText.charAt(i+1) == '/')
				{
					starComment = false;
					if(line != startLine)
					{
						String uniqueId = "CommentID"+ParseUtils.uniqueId(finalString + jSText);
						finalString += uniqueId;
						comments.put(uniqueId, new Comment(currentString +"*/", startLine, line));
					}
					i++;
					continue;
				}
				if(string && c == stringDelimiter)
				{
					string = false;
					String uniqueId = "StringID"+ParseUtils.uniqueId(finalString + jSText);
					finalString += uniqueId;
					StringContainer strC = new StringContainer(currentString + stringDelimiter);
					strC.addErrors(errors);
					errors.clear();
					strings.put(uniqueId, strC);
					continue;
				}
				if(string && c == '\n')
				{
					string = false;
					String uniqueId = "StringID"+ParseUtils.uniqueId(finalString + jSText);
					finalString += uniqueId;
					StringContainer invalid = new StringContainer(currentString);
					invalid.addError(Error.EnterInString);
					invalid.addErrors(errors);
					errors.clear();
					strings.put(uniqueId, invalid);
					finalString += uniqueId + c;
					continue;
				}
				if(string && c == '\\')
				{
					if(i < jSText.length() -1 && allowedCharacters.contains(jSText.charAt(i+1)))
					{
						currentString += c + jSText.charAt(i+1);
						i++;
						continue;
					}
					else
						errors.add(Error.InvalidEscape);
						
				}
				currentString += c;
			}
		}
		if(string)
		{
			string = false;
			String uniqueId = "StringID"+ParseUtils.uniqueId(finalString + jSText);
			finalString += uniqueId;
			StringContainer invalid = new StringContainer(currentString);
			invalid.addError(Error.EnterInString);
			invalid.addErrors(errors);
			errors.clear();
			strings.put(uniqueId, invalid);
			finalString += uniqueId;
		}
		if(starComment)
		{
			starComment = false;
			String uniqueId = "CommentID"+ParseUtils.uniqueId(finalString + jSText);
			finalString += uniqueId;
			Comment comm = new Comment(currentString, startLine, line);
			comm.addError();
			comments.put(uniqueId, comm);
		}
		return new Triple(finalString, comments, strings);
	}

	public static String removeCommentsFromLine(String javaScriptTextString) {
		Matcher m = Patterns.commentLine.matcher(javaScriptTextString);
		while(m.find())
		{
			javaScriptTextString = javaScriptTextString.replace(m.group(), "");
		}
		return javaScriptTextString;
	}
	public static Pair<String, String> splitBlock(Instruction instruction, String in) throws WrongComplexException
	{
		List<Character>forbiden;
		String header;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplex, instruction)).matcher(in);
		int opened = 1;
		String condition, statements;
		if(checkBeginning.find())
		{
			header = checkBeginning.group();
			in = in.replace(header, "");
		}
		else 
			throw new WrongComplexException(Error.IvalidBeginning, in);
		
		switch(instruction){
		case FOR:
			forbiden = Arrays.asList('{', '}');
			break;
		case FUNCITON:
			forbiden = new LinkedList<Character>();
			break;
		default:
			forbiden = Arrays.asList('{', '}', ';');
		}
		if( !instruction.equals(Instruction.TRY) && !instruction.equals(Instruction.ELSE))
		{
			for(int i = 0; i < in.length(); i++)
			{
				if(forbiden.contains(in.charAt(i)))
					throw new WrongComplexException(Error.ForbidenCharacterInHeader, in);
				if(in.charAt(i) == '(')
					opened++;
				if(in.charAt(i) == ')')
					opened--;
				if(opened == 0)
				{
					condition = in.substring(0, i);
					Matcher states = Patterns.states.matcher(in.substring(i+1));
					if (states.find())
						statements = states.group();
					else 
						throw new WrongComplexException(Error.InvalidBlock, in);
					return new Pair<String, String>(condition, statements);
				}
			}
			throw new WrongComplexException(Error.InvalidCondition, in);
		}
		else
		{
			Matcher states = Patterns.states.matcher(in);
			if (states.find())
				statements = states.group();
			else 
				throw new WrongComplexException(Error.InvalidBlock, in);
			return new Pair<String, String>(header, statements);
			
		}

	}
//	public static Pair<String, Map<String, StringContainer>> takeOutStringsAndComents (String javaScriptText){
//		Pair<String, Map<String, StringContainer>> pair;
//		Map<String, StringContainer> stringMap = new HashMap<>();
//		Matcher matcherStringsAndComents = Patterns.stringsAndComents.matcher(javaScriptText);
//		while(matcherStringsAndComents.find()){
//			if(matcherStringsAndComents.group().equals("//")||matcherStringsAndComents.group().equals("/*")){
//				javaScriptText=removeComments(javaScriptText);
//			}else{				
//					pair=takeOutStrings(javaScriptText, stringMap);
//					javaScriptText=pair.getKey();
//					stringMap=pair.getValue();
//
//			}
//		}
//		return new Pair<String, Map<String, StringContainer>> (javaScriptText, stringMap);
//	}
}


