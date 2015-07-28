
package parser;


import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.Error;
import enums.Instruction;
import exception.WrongComplexException;
import javafx.util.Pair;


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
		if (!matcher.find())
			throw new IllegalStateException();
		return matcher.group();
	}

	public static int getLine(List<String> instructions, String group) {
		group = cleanLine(group);
		for (int i = 0; i < instructions.size(); i++)
			try {
				if (group.contains(cleanLine(instructions.get(i))))
					return i + 1;
			} catch (IllegalStateException e) {
			}
		return -2;
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

	public static Pair<String, Map<String, StringContainer>> takeOutStrings(String javaScriptText,
			Map<String, StringContainer> stringMap) {
		StringContainer stringInTexst = new StringContainer("");
		Boolean isInString = false;
		char doubleQuotes = '"';
		char quotes = '\'';
		for (int iterator = 0; iterator < javaScriptText.length(); iterator++) {
			
			if (iterator+1==javaScriptText.length()||isInString)
				stringInTexst.addError(Error.InvalidString);
			
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
					String uniqueId = ParseUtils.uniqueId(javaScriptText);
					stringMap.put("StringID" + uniqueId, stringInTexst);
					javaScriptText = javaScriptText.replace(stringInTexst.string, "StringID" + uniqueId);
					break;
				}
			}
			if (isInString) {
				stringInTexst.string += javaScriptText.charAt(iterator);
				if (javaScriptText.charAt(iterator) == '\n') {
					stringInTexst.addError(Error.InvalidString);
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
						stringInTexst.addError(Error.InvalidString);
					}
				}
			}
		}

		return new Pair<String, Map<String, StringContainer>>(javaScriptText, stringMap);
	}

	public static String removeComments(String javaScriptTextString) {
		boolean lineComment = false;
		boolean starComment = false;
		String enterCounter="";
		String commentedText="";
		for (int iterator = 0; iterator < javaScriptTextString.length(); iterator++) {
			if (javaScriptTextString.charAt(iterator)=='/' && iterator+1!=javaScriptTextString.length()){
				if(javaScriptTextString.charAt(iterator+1)=='/'){
					lineComment =true;
				}
				if(javaScriptTextString.charAt(iterator+1)=='*'){
					starComment =true;
				}
			}
			if (lineComment){
				commentedText+=javaScriptTextString.charAt(iterator);
				if(javaScriptTextString.charAt(iterator)=='\n'){
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

	public static Pair<String, String> splitBlock(Instruction instruction, String in) throws WrongComplexException {
		List<Character> forbiden;
		String header;
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplex, instruction)).matcher(in);
		int opened = 1;
		String condition, statements;
		if (checkBeginning.find()) {
			header = checkBeginning.group();
			in = in.replace(header, "");
		} else
			throw new WrongComplexException(Error.IvalidBeginning, in);

		switch (instruction) {
		case FOR:
			forbiden = Arrays.asList('{', '}');
			break;
		case FUNCITON:
			forbiden = new LinkedList<Character>();
			break;
		default:
			forbiden = Arrays.asList('{', '}', ';');
		}
		if (!instruction.equals(Instruction.TRY) && !instruction.equals(Instruction.ELSE)) {
			for (int i = 0; i < in.length(); i++) {
				if (forbiden.contains(in.charAt(i)))
					throw new WrongComplexException(Error.ForbidenCharacterInHeader, in);
				if (in.charAt(i) == '(')
					opened++;
				if (in.charAt(i) == ')')
					opened--;
				if (opened == 0) {
					condition = in.substring(0, i);
					Matcher states = Patterns.states.matcher(in.substring(i + 1));
					if (states.find())
						statements = states.group();
					else
						throw new WrongComplexException(Error.InvalidBlock, in);
					return new Pair<String, String>(condition, statements);
				}
			}
			throw new WrongComplexException(Error.InvalidCondition, in);
		} else {
			Matcher states = Patterns.states.matcher(in);
			if (states.find())
				statements = states.group();
			else
				throw new WrongComplexException(Error.InvalidBlock, in);
			return new Pair<String, String>(header, statements);

		}

	}

	public static Pair<String, Map<String, StringContainer>> takeOutStringsAndComents(String javaScriptText) {
		Pair<String, Map<String, StringContainer>> pair;
		Map<String, StringContainer> stringMap = new HashMap<>();
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
		return new Pair<String, Map<String, StringContainer>>(javaScriptText, stringMap);
	}
}
