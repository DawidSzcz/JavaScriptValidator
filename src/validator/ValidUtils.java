package validator;

import java.util.LinkedList;
import java.util.List;

import enums.Error;
import expression.Expression;

public class ValidUtils {

	public static final String html = "<html>" + "<head>"
			+ "<link rel=\"stylesheet\" type=\"text/css\" href=\"outStyle.css\">" + "</head>" + "<body> " + "<table>"
			+ "<tr><th>Lp.</th><th>Code</th><th>Errors</th></tr>" + "%s<table>" + "</body>";
	public static final String row = "<tr><td class=\"lp\">%d</td><td style=\"padding-left:%dpx\" class=\"code\">%s</td><td class=\"%s\">%s</td></tr>";

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

	public static String prepareErrors(List<Expression> exps, String language) {
		List<Error> errors = new LinkedList<Error>();
		for (Expression exp : exps)
			errors.addAll(exp.getErrors());
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
		javaScirptText = javaScirptText.replace("\"", "&#x22;");
		return javaScirptText;
	}

	public static String prepareExpressions(List<Expression> list, String language) {
		if(list.size() == 1)
			return list.get(0).toString();
		String data = "<select >\n";
		for(Expression exp : list)
			data += "<option>\n" + exp.toString() + "</option>";
		return data + "\n</select>";
	}

	public static boolean hasErrors(List<Expression> list) {
		for(Expression exp : list)
			if(exp.hasErrors())
				return true;
		return false;
	}

}
