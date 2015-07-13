package validator;

import java.util.List;

import enums.Error;
import expression.Expression;

public class ValidUtils {
	
	public static final String html = "<html>"
			+ "<head>"
			+"<link rel=\"stylesheet\" type=\"text/css\" href=\"outStyle.css\">"
			+ "</head>"
			+ "<body> "
			+ "<table>"
			+ "<tr><th>Lp.</th><th>Code</th><th>Errors</th></tr>"
			+ "%s<table>"
			+ "</body>";
	public static final String row = "<tr><td class=\"lp\">%d</td><td style=\"padding-left:%dpx\" class=\"code\">%s</td><td class=\"%s\">%s</td></tr>";
	
	public static int countSpace(String row) {
		int result = 0;
		for(int i = 0;  row.length() > i && (row.charAt(i) == '\t' || row.charAt(i) == ' ') ; i++)
		{
			if(row.charAt(i) == '\t')
				result+=4;
			else
				result++;
		}
		return result*5;
	}
	public static String prepareErrors(Expression exp)
	{
		List<Error> errors = exp.getErrors();
		if(errors.size() == 1)
			return errors.get(0).toString();
		else
		{
			String data = "<select >\n";
			for(Error message : errors)
				data += "<option>\n" + message.toString() + "</option>";
			return data + "\n</select>";
		}
	}
}