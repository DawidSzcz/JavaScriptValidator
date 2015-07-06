package Validator;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationM {

	public static String comentaryVariable(ArrayList<String> javaScriptText) {
		String errorMassage = " ERROR";
		String newTekst = new String();
		Boolean Error = false;
		Boolean addTekst = true;
		int licznik = 0;
		for (int iterator2 = 0; iterator2 < javaScriptText.size(); iterator2++) {
			String row = javaScriptText.get(iterator2);
			for (int iterator = 0; iterator < row.length(); iterator++) {

				if (iterator != row.length() - 1 && row.charAt(iterator) == '/' && row.charAt(iterator + 1) == '*') {
					licznik++;
					addTekst = false;
				}

				if (addTekst)
					newTekst += javaScriptText.get(iterator2).charAt(iterator);

				if (iterator != 0 && row.charAt(iterator - 1) == '*' && row.charAt(iterator) == '/') {
					licznik--;
					addTekst = true;
				}

				if (licznik < 0) {
					Error = true;
				}
			}
		}

		if (licznik != 0) {
			Error = true;
		}
		if (Error)
			newTekst += errorMassage;

		return newTekst;
	}

	public static ArrayList<String> group(String javaScriptText) {
		ArrayList<String> functionList = new ArrayList<String>();
		String function = "";
		int iloscpetli = 0;
		while (true) {
			Pattern pattern = Pattern.compile("[^\\n]+[\\s]+\\{[^\\}\\{]+\\}");
			Matcher matcher = pattern.matcher(javaScriptText);
			if (matcher.find()) {
				function = matcher.group();
				functionList.add(function);
				javaScriptText = javaScriptText.replace(function, "[" + Integer.toString(iloscpetli) + "]");
				++iloscpetli;
			} else {
				break;
			}
		}

		functionList.add(javaScriptText);

		return functionList;
	}

	public static String sqlCorrect(String javaScriptText) {
		String errorMassage = "";
		String findSQLQery = "select [^\\;]+ from |CREATE TABLE [^\\;,]+ \\(";
		String executeSQL = ".executeStatement";
		String getPort = ".getTytanDBPortFeature()";
		String errorExecuteSQL = "function \"executeStatement\" is incorrect";
		String errorGetPort = "function \"getTytanDBPortFeature()\" is incorrect";
		int errorindex;

		Pattern pattern = Pattern.compile(findSQLQery);
		Matcher matcher = pattern.matcher(javaScriptText);
		if (matcher.find()) {
			errorindex = javaScriptText.indexOf(executeSQL);
			if (errorindex == -1) {
				errorMassage += errorExecuteSQL + " ";
			}
			errorindex = javaScriptText.indexOf(getPort);
			if (errorindex == -1) {
				errorMassage += errorGetPort + " ";
			}

		}

		return errorMassage;
	}

	// operatory reg
	// \w+\s*=\s*[\w"']+|\w+\s*==\s*[\w"']+|\w+\s*!=\s*[\w"']+|\w+\s*>\s*[\w"']+|\w+\s*<\s*[\w"']+|\w+\s*<=\s*[\w"']+|\w+\s*>=\s*[\w"']+|\w+\s*===\s*[\w"']+|\w+\s*!==\s*[\w"']+
	public static String opreratorCorrect(String javaScriptText) {
		String errorMassage = "";
		String errorOperator = "Operator is incoroect";
		String regOperator = "\\w+\\s*=\\s*[\\w\"']+|\\w+\\s*==\\s*[\\w\"']+|\\w+\\s*!=\\s*[\\w\"']+|\\w+\\s*>=\\s*[\\w\"']+|\\w+\\s*<=\\s*[\\w\"']+|\\w+\\s*<\\s*[\\w\"']+|\\w+\\s*>\\s*[\\w\"']+|\\w+\\s*===\\s*[\\w\"']+|\\w+\\s*!==\\s*[\\w\"']+";

		Pattern pattern = Pattern.compile(regOperator);
		Matcher matcher = pattern.matcher(javaScriptText);
		int errorIndex;

		while (matcher.find()) {
			javaScriptText = javaScriptText.replace(matcher.group(), "");
			matcher = pattern.matcher(javaScriptText);
		}
		errorIndex = javaScriptText.indexOf("=");
		if (errorIndex != -1) {
			errorMassage = errorOperator;
		}
		errorIndex = javaScriptText.indexOf("<");
		if (errorIndex != -1) {
			errorMassage = errorOperator;
		}
		errorIndex = javaScriptText.indexOf(">");
		if (errorIndex != -1) {
			errorMassage = errorOperator;
		}
		return errorMassage;
	}

}
