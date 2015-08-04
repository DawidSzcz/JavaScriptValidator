
package parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.StringContainer;
import javafx.util.Pair;
import enums.Error;

public class ParseUtils {
	private static List<Character> allowedCharacters = Arrays.asList('\'', '"', 'f', 'n', '\\', 'r', 't', 'b');
	public static class Triple{
		public final String header;
		public final String statements;
		public final int lines;
		public final int linesBeforeStatement;
		public Triple(String h, String s, int l, int lbs)
		{
			header = h;
			statements = s;
			lines = l;
			linesBeforeStatement = lbs;
		}
	}
	public static String cleanLine(String statement) throws IllegalStateException
	{
		statement = statement.replace("[ \t\r]+", " ");
		Matcher matcher = Patterns.escapeWhiteSpace.matcher(statement);
		if(!matcher.find())
			throw new IllegalStateException();
		return matcher.group();
	}
	public static String uniqueId(String in) {
		Random rand = new Random();
		long x = rand.nextLong();
		String randomString = String.valueOf(x > 0 ? x : -x);
		while (in.contains(randomString)) {
			x = rand.nextLong();
			randomString = String.valueOf(x > 0 ? x : -x);
		}
		return randomString;
	}

	public static Pair<String, HashMap<String, StringContainer>> takeOutStrings(String javaScriptText,
			HashMap<String, StringContainer> stringMap) {
		StringContainer stringInTexst = new StringContainer("");
		Boolean isInString = false;
		char doubleQuotes = '"';
		char quotes = '\'';
		for (int iterator = 0; iterator < javaScriptText.length(); iterator++) {
			String uniqueId = ParseUtils.uniqueId(javaScriptText);
			
			if (javaScriptText.charAt(iterator) == doubleQuotes || javaScriptText.charAt(iterator) == quotes) {
				if (javaScriptText.charAt(iterator) == '"') {
					quotes = '"';
				}
				if (javaScriptText.charAt(iterator) == '\'') {
					doubleQuotes = '\'';
				}
				if (!isInString) {
					isInString = true;
				} else {
					// isInString = false;
					stringInTexst.string += javaScriptText.charAt(iterator);
					stringMap.put("StringID" + uniqueId, stringInTexst);
					javaScriptText = javaScriptText.replace(stringInTexst.string, "StringID" + uniqueId+" ");
					break;
				}
			}
			if (isInString) {
				stringInTexst.string += javaScriptText.charAt(iterator);
				if (javaScriptText.charAt(iterator) == '\n' || iterator==javaScriptText.length()-1) {
					stringInTexst.addError(Error.EnterInString);
					stringMap.put("StringID" + uniqueId, stringInTexst);
					javaScriptText = javaScriptText.replace(stringInTexst.string, "StringID" + uniqueId);
					break;
				}
				if (javaScriptText.charAt(iterator) == '\\' && iterator + 1 < javaScriptText.length()) {
					if (javaScriptText.charAt(iterator + 1) == '\\' || javaScriptText.charAt(iterator + 1) == 'n'
							|| javaScriptText.charAt(iterator + 1) == 'r' || javaScriptText.charAt(iterator + 1) == 't'
							|| javaScriptText.charAt(iterator + 1) == 'b'
							|| javaScriptText.charAt(iterator + 1) == 'f') {
						
					} else if (javaScriptText.charAt(iterator + 1) == '\''
							|| javaScriptText.charAt(iterator + 1) == '\"') {
						stringInTexst.string += javaScriptText.charAt(iterator) + javaScriptText.charAt(iterator + 1);
						++iterator;
					} else {
						stringInTexst.addError(Error.InvalidEscape);
					}
				}
			}
		}

		return new Pair<String, HashMap<String, StringContainer>>(javaScriptText, stringMap);
	}
	
	

	public static String removeComments(String javaScriptTextString) {
		boolean lineComment = false;
		boolean starComment = false;
		String enterCounter="";
		String commentedText="";
		for (int iterator = 0; iterator < javaScriptTextString.length(); iterator++) {
			if (javaScriptTextString.charAt(iterator)=='/' && iterator+1!=javaScriptTextString.length()){
				if(javaScriptTextString.charAt(iterator+1)=='/' && !starComment){
					lineComment =true;
				}
				if(javaScriptTextString.charAt(iterator+1)=='*' && !lineComment){
					starComment =true;
				}
			}
			if (lineComment){
				commentedText+=javaScriptTextString.charAt(iterator);

				if(javaScriptTextString.charAt(iterator)=='\n' || iterator==javaScriptTextString.length()-1 ){

					javaScriptTextString=javaScriptTextString.replace(commentedText, "\n");
					break;//lineComment=false;
				}

			}
			if (starComment){
				commentedText+=javaScriptTextString.charAt(iterator);
				if(javaScriptTextString.charAt(iterator)=='\n'){
					enterCounter+='\n';
				}
				if( iterator>0 && javaScriptTextString.charAt(iterator-1)=='*'&& javaScriptTextString.charAt(iterator)=='/'){
					javaScriptTextString=javaScriptTextString.replace(commentedText,enterCounter );
					break;//starComment=false;
				}
			}
			
		}
		return javaScriptTextString;
	}


	public static String removeCommentsFromLine(String javaScriptTextString) {
		Matcher m = Patterns.commentLine.matcher(javaScriptTextString);
		while (m.find()) {
			javaScriptTextString = javaScriptTextString.replace(m.group(), "");
		}
		return javaScriptTextString;
	}


	public static int getLinesBNS(String substring) {
		Matcher space = Pattern.compile("^\\s+").matcher(substring);
		
		return space.find() ? getLines(space.group()) : 0;
	}
	public static int getLines(String statement, HashMap<String, String> blocks) {
		Object[] keys = blocks.keySet().toArray();
		for(int i = 0; i < keys.length; i++)
		{
			if(statement.contains((String)keys[i]))
			{
				statement= statement.replaceAll((String)keys[i], blocks.get((String)keys[i]));
				i = 0;
			}
		}
		return  getLines(statement);
	}
	public static int getLines(String statement) {
		int newLines = 0;
		for(int i = 0; i < statement.length(); i++)
			if(statement.charAt(i) == '\n')
				newLines++;
		return newLines;
	}

	public static Pair<String, HashMap<String, String>> removeBlocks(String input) {
		HashMap<String, String> blocks = new HashMap<String, String>();
		Matcher mat = Patterns.block.matcher(input);
		while (mat.find()) {
			String block = mat.group();
			String uniqueId = "BlockID"+ParseUtils.uniqueId(input);
			if (input.contains(block))
				input = input.replace(block, uniqueId + ";");
			blocks.put(uniqueId, block);
			mat = Patterns.block.matcher(input);
		}
		return new Pair<String, HashMap<String, String>>(input, blocks);
	}
	public static Pair<String, HashMap<String, StringContainer>> takeOutStringsAndComents(String javaScriptText) {
		Pair<String, HashMap<String, StringContainer>> pair;
		HashMap<String, StringContainer> stringMap = new HashMap<>();
		Matcher matcherStringsAndComents = Patterns.stringsAndComents.matcher(javaScriptText);
		while (matcherStringsAndComents.find()) {
			if (matcherStringsAndComents.group().equals("//") || matcherStringsAndComents.group().equals("/*")) {
				javaScriptText = removeComments(javaScriptText);
			} else {
				pair = takeOutStrings(javaScriptText, stringMap);
				javaScriptText = pair.getKey();
				stringMap = pair.getValue();

			}
			matcherStringsAndComents = Patterns.stringsAndComents.matcher(javaScriptText);
		}
		return new Pair<String, HashMap<String, StringContainer>>(javaScriptText, stringMap);
	}
	public static Pair<String, HashMap<String, StringContainer>> removeStrAndCom(String jSText) 
	{
		boolean inlineComment = false, starComment = false, string= false;
		char stringDelimiter = '"';
		List<Error> errors = new LinkedList<Error>();
		String finalString = "", currentString = "";
		HashMap<String, StringContainer> strings = new HashMap<String, StringContainer>();
		for(int i = 0; i< jSText.length(); i++)
		{
			char c = jSText.charAt(i);
			if(!inlineComment && !starComment && !string)
			{
				if(c == '/' && i != jSText.length() -1 && jSText.charAt(i+1) == '/')
				{
					inlineComment = true;
					i++;
					continue;
				}
				if(c == '/' && i != jSText.length() -1 && jSText.charAt(i+1) == '*')
				{
					starComment = true;
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
				if(inlineComment && c == '\n')
				{
					inlineComment = false;
					finalString += '\n';
					continue;
				}
				if(starComment && c == '*' && jSText.charAt(i+1) == '/')
				{
					starComment = false;
					finalString += currentString;
					currentString = "";
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
					currentString = "";
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
				if(string || c == '\n')
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

		return new Pair<String, HashMap<String, StringContainer>>(finalString, strings);
	}
	public static boolean checkBetweenCondStates(String substring, String content) 
	{
		String between = substring.substring(0, substring.indexOf(content));
		return !Pattern.compile("[\\w\\(\\)\\$_]+").matcher(between).find();
	}
}
//private List<Expression> secondExpression(Expression exp, String statement) {
//	if (statement.contains("{")) {
//		exp.addError(Error.UnexpectedOpeningBracket);
//		return parseExpressions(statement.split("\\{")[1], 0);
//	}
//	Matcher match = Patterns.checkOpenning.matcher(statement);
//	if (match.find()){
//		exp.addError(Error.MissingOpenningBracket);
//		match = Patterns.secondLine.matcher(statement);
//		match.find();
//		match.find();
//		return parseExpressions(match.group(), 0);
//	}
//	return new LinkedList<Expression>();
//}
//if(starComment)
//{
//	starComment = false;
//	String uniqueId = "CommentID"+ParseUtils.uniqueId(finalString + jSText);
//	finalString += uniqueId;
//	Comment comm = new Comment(currentString, startLine, line);
//	comm.addError();
//	comments.put(uniqueId, comm);
//}
