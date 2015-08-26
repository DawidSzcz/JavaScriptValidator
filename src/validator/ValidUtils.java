package validator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Atoms.StringContainer;
import enums.Error;
import expression.Expression;
import javafx.util.Pair;
import parser.ParseUtils;

public class ValidUtils {

	public static final String html = "<html>" + "<head>"
			+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"outStyle.css\">" + "</head>" + "<body> " + "<table>"
			+ "<tr><th>Lp.</th><th>Code</th><th>Errors</th></tr>" + "%s<table>" + "</body>";
	public static final String row = "<tr><td class=\"lp\">%d</td><td style=\"padding-left:%dpx\" class=\"code\">%s</td><td class=\"%s\">%s</td></tr>";
	
	public static List<Character> special = Arrays.asList('{','}', '(', ')', ' ', '\t', '\n', ';', '\r');
	public static List<String> restrictedWords = Arrays.asList(
		"abstract", "arguments", "boolean", "break", "byte",
		"case", "catch", "char", "class", "const", 
		"continue", "debugger", "default", "delete", "do", 
		"double", "else", "enum", "eval", "export", "extends", "false", "final", "finally", "float", 
		"for", "function", "goto", "if", "implements", "import", 
		"in", "instanceof", "int", "interface", 
		"let", "long", "native", "new", "null", 
		"package", "private", "protected", "public", "return", 
		"short", "static", "super", "switch", "synchronized", 
		"this", "throw", "throws", "transient", "true", 
		"try", "typeof", "var", "void", "volatile", 
		"while", "with", "yield");

	public static int countSpace(String row) {
		int result = 0;
		for (int i = 0; row.length() > i && (row.charAt(i) == '\t' || row.charAt(i) == ' '); i++) {
			if (row.charAt(i) == '\t')
				result += 4;
			else
				result++;
		}
		return result * 5;
	}

	public static String prepareErrors(List<Expression> exps, int i, String language) {
		List<Error> errors = new LinkedList<Error>();
		for (Expression exp : exps)
			errors.addAll(exp.getErrors(i));
		if (errors.size() == 1) {
			if (language.equals("English")) {
				return errors.get(0).enContent;
			} else {
				return errors.get(0).plContent;
			}
		} else {
			String data = "<select >\n";
			for (Error message : errors) {
				if (language.equals("English"))
					data += "<option>\n" + message.enContent + "</option>";
				else
					data += "<option>\n" + message.plContent + "</option>";
			}
			return data + "\n</select>";
		}
	}

	public static String htmlValidReplace(String javaScirptText) {
		javaScirptText = javaScirptText.replace("&", "&#x26;");
		javaScirptText = javaScirptText.replace("<", "&#x3C;");
		javaScirptText = javaScirptText.replace(">", "&#x3E;");
		//javaScirptText = javaScirptText.replace("\"", "&#x22;");
		return javaScirptText;
	}

	public static String prepareExpressions(List<Expression> list, String language) {
		if(list.size() == 1)
			return list.get(0).toString() + (list.get(0).hasErrors() ? " <b>Error</b>" : "");
		String data = "<select >\n";
		for(Expression exp : list)
			data += "<option>\n" + exp.toString() + (exp.hasErrors() ? " <b>Error</b>" : "" )+ "</option>";
		return data + "\n</select>";
	}

	public static boolean hasErrors(List<Expression> list, int i) {
		for(Expression exp : list)
			if(exp.hasErrors(i))
				return true;
		return false;
	}

	public static String color(String jSText) 
	{
		String comment = "<span class=\"comment\">", str = "<span class=\"string\">", spanEnd = "</span>", restr = "<span class=\"restricted\">";
		boolean inlineComment = false, starComment = false, string= false;
		char stringDelimiter = '"';
		String finalString = "";
		for(int i = 0; i< jSText.length(); i++)
		{
			char c = jSText.charAt(i);
			if(!inlineComment && !starComment && !string)
			{
				if(i == 0 || special.contains(jSText.charAt(i-1)))
				{
					String temp = jSText.substring(i);
					for(String restricted : restrictedWords)
						if(temp.startsWith(restricted)&& (temp.length() == restricted.length() || special.contains(temp.charAt(restricted.length()))))
						{
							finalString+= restr + restricted + spanEnd;
							i+= restricted.length();
							if(i == jSText.length())
								return finalString;
							c = jSText.charAt(i);
							break;
						}
				}
				if(c == '/' && i != jSText.length() -1 && jSText.charAt(i+1) == '/')
				{
					inlineComment = true;
					i++;
					finalString += comment+"//";
					continue;
				}
				if(c == '/' && i != jSText.length() -1 && jSText.charAt(i+1) == '*')
				{
					starComment = true;
					i++;
					finalString += comment+"/*";
					continue;
				}
				if(c == '"' || c == '\'')
				{
					string = true;
					finalString += str+c;
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
					finalString += spanEnd+'\n';
					continue;
				}
				if(starComment && c == '*' && jSText.charAt(i+1) == '/')
				{
					starComment = false;
					finalString += "*/"+spanEnd;
					i++;
					continue;
				}
				if(string && c == stringDelimiter)
				{
					string = false;
					finalString += c+spanEnd;
					continue;
				}
				if(string && c == '\n')
				{
					string = false;
					finalString += c+spanEnd;
					continue;
				}
				if(string && c == '\\' && i < jSText.length() -1 && jSText.charAt(i+1) == stringDelimiter)
				{
					finalString += c + stringDelimiter;
					i++;
					continue;
						
				}
				if(starComment && c == '\n')
				{
					finalString+=spanEnd+c+comment;
					continue;
				}
				finalString+=c;
				
			}
		}
		if(string || starComment || inlineComment)
			finalString += spanEnd;
		return finalString;
	}
}
