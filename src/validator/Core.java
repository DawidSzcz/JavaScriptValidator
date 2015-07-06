package validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.WrongWhileException;
import expression.Expression;
import expression.ExpressionIterator;
import expression.ExpressionParser;
import expression.Patterns;

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
	private final String row = "<tr><td class=\"lp\">%d</td><td style=\"padding-left:%dpx\" class=\"code\">%s</td><td class=\"error\">%s</td></tr>";
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		ExpressionParser parser = new ExpressionParser();
		try {
			List<Expression> list = parser.Parse(request.getParameter("javaScript"));

		List<String> rows = Arrays.asList(request.getParameter("javaScript").split("\n"));
		//List<List<String>> messages = ValidationDawid.countBrackets(rows);
		
		out.println(String.format(html, makeResponse(rows, list)));
		
		} catch (WrongWhileException e) {
			out.println("Somfin gone wrong");
		}
	}
	private String makeResponse(List<String> rows, List<Expression> list)
	{
		String body = "";
		ExpressionIterator iterator = new ExpressionIterator(list);
		Matcher singleState;
		for(int i = 0; i < rows.size(); i++)
		{
			String rowMess = "nvm";
			singleState = Patterns.sinState.matcher(rows.get(i));
			if(singleState.find())
			{
				String next = iterator.next();
				rowMess = next != null ? next :"nvm";
			}
			body += String.format(row, i, countSpace(rows.get(i)), rows.get(i), rowMess);
		}
		return body;
	}
	private int countSpace(String row) {
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
} 