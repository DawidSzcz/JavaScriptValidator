package validator;

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
	
	public static  int countSpace(String row) {
		int tabs = 0;
		while(row.charAt(tabs) == '\t' || row.charAt(tabs) == ' ')
		{
			if(row.charAt(tabs) == '\t')
				tabs+=4;
			else
				tabs++;
		}
		return tabs*5;
	}
}

/*for(int i = 0; i < rows.size(); i++)
{
	String rowMess = "";
	if(!messages.get(i).isEmpty())
	{
		rowMess = "<select>\n";
		for(String message : messages.get(i))
			rowMess += "<option>\n" + message + "</option>";
		rowMess += "</select>";
	}
	body += String.format(row, i, rows.get(i), rowMess);
}*/