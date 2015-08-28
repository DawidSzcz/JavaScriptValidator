
package parser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.InputContainer;
import Atoms.Statement;
import Atoms.StringContainer;
import javafx.util.Pair;
import validator.Context;
import enums.Error;
import enums.Instruction;


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

	public static InputContainer takeOutStringsAndComents(String javaScriptTextString) {
		InputContainer  javaScriptText= new InputContainer(javaScriptTextString);
		Matcher matcherStringsAndComents = Patterns.stringsAndComents.matcher(javaScriptText.getString());
		while (matcherStringsAndComents.find()) {
			if (matcherStringsAndComents.group().equals("//") || matcherStringsAndComents.group().equals("/*")) {
				javaScriptText = removeComments(javaScriptText);
			} else {
				javaScriptText.setString(takeOutStrings(javaScriptText.getString()));
			}
			matcherStringsAndComents = Patterns.stringsAndComents.matcher(javaScriptText.getString());
		}
		return javaScriptText;
	}
	
	public static String replaceFirst (String find, String replace, String text){
		int begin = text.indexOf(find);
		int end = begin+find.length();
		text=text.substring(0, begin)+replace+text.substring(end);
		return text;
	}
	
	public static String numberOfEnter(String expression){
		String enters="";
		for( int i=0 ; i<expression.length(); i++){
			if(expression.charAt(i)=='\n'){
				enters+="\n";
			}
		}
		
		return enters;
	}
	
	public static String takeOutStrings(String javaScriptText) {
		StringContainer stringInText = new StringContainer("");
		Boolean isInString = false;
		char delimiter = 'u';
		String content = "";
		int line = 1;
		for (int iterator = 0; iterator < javaScriptText.length(); iterator++) {
			String uniqueId = ParseUtils.uniqueId(javaScriptText);
			
			if (!isInString && (javaScriptText.charAt(iterator) == '"' || javaScriptText.charAt(iterator) == '\'')) {
				delimiter = javaScriptText.charAt(iterator);
				isInString = true;
				content += javaScriptText.charAt(iterator);
				continue;
			}
			if(isInString && javaScriptText.charAt(iterator) == delimiter)
			{
				content+= delimiter;
				stringInText.setString(stringInText.getString()+javaScriptText.charAt(iterator));
				Context.strings.put("StringID" + uniqueId, stringInText);
				javaScriptText = ParseUtils.replaceFirst(content, "StringID" + uniqueId + " ", javaScriptText);
				stringInText.setString(content);
				stringInText.setLine(line);
				break;
			}
			if (isInString) {
				if (javaScriptText.charAt(iterator) == '\n' || iterator==javaScriptText.length()-1) {
					stringInText.addError(Error.EnterInString);
					Context.strings.put("StringID" + uniqueId, stringInText);
					javaScriptText = ParseUtils.replaceFirst(content, "StringID" + uniqueId + " ", javaScriptText);
					stringInText.setString(content);
					stringInText.setLine(line);
					isInString = false;
					break;
				}
				content += javaScriptText.charAt(iterator);
				if (javaScriptText.charAt(iterator) == '\\' && iterator + 1 < javaScriptText.length()) {
					if (javaScriptText.charAt(iterator + 1) == '\\' || javaScriptText.charAt(iterator + 1) == 'n'
							|| javaScriptText.charAt(iterator + 1) == 'r' || javaScriptText.charAt(iterator + 1) == 't'
							|| javaScriptText.charAt(iterator + 1) == 'b'
							|| javaScriptText.charAt(iterator + 1) == 'f') {
						
					} else if (javaScriptText.charAt(iterator + 1) == '\''
							|| javaScriptText.charAt(iterator + 1) == '\"') {
						stringInText.setString(stringInText.getString()+javaScriptText.charAt(iterator) + javaScriptText.charAt(iterator + 1));
						++iterator;
					} else {
						stringInText.addError(Error.InvalidEscape);
					}
				}
			}
			if(javaScriptText.charAt(iterator) == '\n')
				line++;
		}

		return javaScriptText;
	}
	
	

	public static InputContainer removeComments(InputContainer javaScriptText) {
		boolean lineComment = false;
		boolean starComment = false;
		String enterCounter="";
		String commentedText="";
		for (int iterator = 0; iterator < javaScriptText.getString().length(); iterator++) {
			if (javaScriptText.getString().charAt(iterator)=='/' && iterator+1!=javaScriptText.getString().length()){
				if(javaScriptText.getString().charAt(iterator+1)=='/' && !starComment){
					lineComment =true;
				}
				if(javaScriptText.getString().charAt(iterator+1)=='*' && !lineComment){
					starComment =true;
				}
			}
			if (lineComment){
				commentedText+=javaScriptText.getString().charAt(iterator);

				if(javaScriptText.getString().charAt(iterator)=='\n' || iterator==javaScriptText.getString().length()-1 ){

					javaScriptText.setString(javaScriptText.getString().replace(commentedText, "\n"));
					break;//lineComment=false;
				}

			}
			if (starComment){
				commentedText+=javaScriptText.getString().charAt(iterator);
				if(javaScriptText.getString().charAt(iterator)=='\n'){
					enterCounter+='\n';
				}
				if( iterator>0 && javaScriptText.getString().charAt(iterator-1)=='*'&& javaScriptText.getString().charAt(iterator)=='/'){
					javaScriptText.setString(javaScriptText.getString().replace(commentedText,enterCounter ));
					break;//starComment=false;
				}
				if (iterator==javaScriptText.getString().length()-1){
					for (int i=0; javaScriptText.getString().charAt(i)!='/' || javaScriptText.getString().charAt(i+1)!='*'; i++){
						if (javaScriptText.getString().charAt(i)=='\n'){
							javaScriptText.addline();
						}
					}
					javaScriptText.setString(javaScriptText.getString().replace(commentedText,enterCounter ));
					javaScriptText.addError(Error.MissingEndOfComment);

				}
			}
		}
		return javaScriptText;
	}


	public static int getArea(String in)
	{
		in = cleanLine(in);
		return getLines(in, new HashMap<String, String>());
	}
	public static int getLinesBNS(String substring) {
		Matcher space = Pattern.compile("^\\s+").matcher(substring);
		
		return space.find() ? getLines(space.group()) : 0;
	}
	public static int getLines(String statement, Map<String, String> blocks) {
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
	public static Pair<String, Map<String, String>> removeBlocks(String input) 
	{
		Pair<String, Map<String, String>> headersRemoved = removeHeaders(input);
		Map<String, String> blocks = headersRemoved.getValue();
		input = headersRemoved.getKey();
		Matcher mat = Patterns.block.matcher(input);
		while (mat.find()) {
			String block = mat.group();
			String uniqueId = "BlockID"+ParseUtils.uniqueId(input);
			if (input.contains(block))
				input = replaceFirst(block, ";" + uniqueId + ";", input);
			blocks.put(uniqueId, block);
			mat = Patterns.block.matcher(input);
		}
		return new Pair<String, Map<String, String>>(input, blocks);
	}
	public static Pair<String, Map<String, String>> removeHeaders(String input) 
	{
		List<Character> forbidden = Arrays.asList(';', '{', '}');
		List<Character> forbiddenFor = Arrays.asList( '{', '}');
		Map<String, String> map = new HashMap<>();
		Matcher MatchH = Patterns.header.matcher(input);
		while(MatchH.find())
		{
			String head = MatchH.group();
			String condition = "";
			String x = ParseUtils.cleanLine(head);
			if(!(head.matches("\\s*try") || head.matches("\\s*else") || x.matches(Instruction.CASE.toString())))
			{
				int opened = 1;
				for (int i = input.indexOf(head) + head.length(); i < input.length(); i++) 
				{
					char c = input.charAt(i);
					if(head.matches("\\s*for\\("))
					{
						if(forbiddenFor.contains(c))
						{
							i--;
							break;
						}
					}
					else
						if(forbidden.contains(c))
						{
							i--;
							break;
						}
					if (c == '(')
						opened++;
					if (c == ')')
						opened--;
					condition += c;
					if (opened == 0) 
						break;
				}
			}
			String ID = headerId(head, input);
			map.put(ID, head + condition);
			input = replaceFirst(head + condition, ID+";", input);
			MatchH = Patterns.header.matcher(input);
		}
		return new Pair<String, Map<String, String>>(input, map);
	}
	private static String headerId(String statement, String input) 
	{
		Matcher matcherFor = Patterns.For.matcher(statement);
		Matcher matcherElse = Patterns.Else.matcher(statement);
		Matcher matcherTry = Patterns.Try.matcher(statement);
		Matcher matcherCatch = Patterns.Catch.matcher(statement);
		Matcher matcherSwich = Patterns.Switch.matcher(statement);
		Matcher matcherIf = Patterns.If.matcher(statement);
		Matcher matcherFunc = Patterns.function.matcher(statement);
		Matcher matcherWhile = Patterns.While.matcher(statement);
		Matcher matcherCase = Patterns.Case.matcher(statement);		
		String id;
		if(matcherElse.find())
			id = "else";
		else if (matcherTry.find())
			id = "try";
			else if (matcherIf.find())
					id = "if";
				else if (matcherSwich.find())
						id = "switch";
					else if (matcherCatch.find())
							id = "catch";
						else if (matcherFunc.find())
								id = "function";
							else if (matcherWhile.find())
									id = "while";
								else if(matcherFor.find())
										id = "for";
									else if(matcherCase.find())
												id = "case";
										else
											id= "ohShit";
		return id+uniqueId(input);
	}
	
	public static boolean checkBetweenCondStates(String substring, String content) 
	{
		String between = substring.substring(0, substring.indexOf(content));
		return !Pattern.compile("[\\w\\(\\)\\$_]+").matcher(between).find();
	}
	public static String cleanWhite(String in)
	{
		String regX = "[^\\s]+(\\s+|$)";
		String str = "";
		Matcher match = Pattern.compile(regX).matcher(in);
		while(match.find())
			str += match.group().trim() +" ";
		return str;
	}
}