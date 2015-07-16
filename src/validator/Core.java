
package validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enums.Error;
import expression.Expression;
import expression.ExpressionIterator;
import expression.ExpressionParser;

public class Core extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		ExpressionParser parser = new ExpressionParser(request.getParameter("javaScript"));
		Expression program = parser.parse(request.getParameter("javaScript"));
		List<String> rows = Arrays.asList(request.getParameter("javaScript").split("\n"));
		out.println(String.format(ValidUtils.html, makeResponse(rows, program)));
	}
	private String makeResponse(List<String> rows, Expression program)
	{
		HashMap<Integer, List<Error>> error = program.getAllErrors();
		String body = "";
		ExpressionIterator iterator = new ExpressionIterator(program);
		Expression next = iterator.next();
		for(int i = 0; i < rows.size(); i++)
		{
			if(next.match(rows.get(i)))
			{
				body += String.format(ValidUtils.row, i+1, ValidUtils.countSpace(ValidUtils.htmlValidReplace(rows.get(i))), rows.get(i), next.hasErrors() ? "error" : "noError", next.hasErrors() ? ValidUtils.prepareErrors(next) : iterator.getTree());
				next = iterator.next();
			}
			else
				body += String.format(ValidUtils.row, i+1, ValidUtils.countSpace(ValidUtils.htmlValidReplace(rows.get(i))), rows.get(i), "plain", "plain");
		}
		return body;
	}
}