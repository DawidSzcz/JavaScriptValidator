
package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.Error;
import exception.EnterInStringError;
import exception.WrongComplexException;
import javafx.util.Pair;

public class ParseUtils {

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
	public static Pair<String, Map<String, String>> takeOutStrings(String javaScriptText) throws EnterInStringError {
		Boolean isInString = false;
		String stringInTexst = "";
		char doubleQuotes='"';
		char quotes= '\'';
		Map<String, String> stringMap = new HashMap<>();
		for (int iterator = 0; iterator < javaScriptText.length(); iterator++) {
			if (javaScriptText.charAt(iterator) == doubleQuotes || javaScriptText.charAt(iterator) == quotes) {
				if (javaScriptText.charAt(iterator) == '"'){
					quotes='"';
				}
				if (javaScriptText.charAt(iterator) == '\''){
					doubleQuotes='\'';
				}
				if (!isInString) {
					isInString = true;
				} else {
					isInString = false;
					stringInTexst+=javaScriptText.charAt(iterator);
					String uniqueId= ParseUtils.uniqueId(javaScriptText);
					stringMap.put("StringID"+uniqueId, stringInTexst);
					javaScriptText=javaScriptText.replace(stringInTexst, "StringID" /*+uniqueId */);
					stringInTexst = "";
					doubleQuotes='"';
					quotes='\'';
					iterator = 0;
				}
			}
			if (isInString) {
				if (javaScriptText.charAt(iterator)!='\n'){
					stringInTexst+=javaScriptText.charAt(iterator);
				}else {
					throw new EnterInStringError(enums.Error.EnterInString,stringInTexst);
				}
			}
		}

		return  new Pair<String, Map<String, String>>(javaScriptText, stringMap);
	}
	
	public static String removeComments(String javaScriptTextString) 
	{
		Matcher m = Patterns.comment.matcher(javaScriptTextString);
		while(m.find())
		{
			javaScriptTextString = javaScriptTextString.replace(m.group(), "");
			m = Patterns.comment.matcher(javaScriptTextString);
		}
		return javaScriptTextString;
		
	}

	public static String removeCommentsFromLine(String javaScriptTextString) {
		Matcher m = Patterns.commentLine.matcher(javaScriptTextString);
		while(m.find())
		{
			javaScriptTextString = javaScriptTextString.replace(m.group(), "");
		}
		return javaScriptTextString;
	}
	public static Pair<String, String> findCondition(String instruction, String in) throws WrongComplexException
	{
		List<Character>forbiden;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplex, instruction)).matcher(in);
		int opened = 1;
		String condition, statements;
		if(checkBeginning.find())
			in = in.replace(checkBeginning.group(), "");
		else 
			throw new WrongComplexException(Error.IvalidBeginning, in);
		
		switch(instruction){
		case "for":
			forbiden = Arrays.asList('{', '}');
			break;
		case "function":
			forbiden = new LinkedList<Character>();
			break;
		default:
			forbiden = Arrays.asList('{', '}', ';');
		}
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
}


