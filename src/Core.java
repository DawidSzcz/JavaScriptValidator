import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Core extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final String html = "<html>"
			+ "<head>"
			+"<link rel=\"stylesheet\" type=\"text/css\" href=\"outStyle.css\">"
			+ "</head>"
			+ "<body> "
			+ "<table>"
			+ "<tr><th>Lp.</th><th>Code</th><th>Errors</th></tr>"
			+ "%s<table>"
			+ "</body>";
	private final String row = "<tr><td class=\"lp\">%d</td><td class=\"code\">%s</td><td class=\"error\">%s</td></tr>";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		List<String> rows = Arrays.asList(request.getParameter("javaScript").replace("\t", "&#9").split("\n"));
		List<List<String>> messages = ValidationDawid.countBrackets(rows);
		
		String body = "";
		
		for(int i = 0; i < rows.size(); i++)
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
		}
		out.println(String.format(html, body));
	}
} 