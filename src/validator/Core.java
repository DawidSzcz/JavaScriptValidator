
package validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enums.Error;
import exception.WrongComplexException;
import exception.WrongWhileException;
import expression.Expression;
import expression.ExpressionIterator;
import expression.Program;
import parser.ExpressionParser;

public class Core extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = response.getWriter();
		try{
		Program program = new Program(request.getParameter("javaScript"));
		List<String> rows = Arrays.asList(request.getParameter("javaScript").split("\n"));
		String language = request.getParameter("language");
		out.println(String.format(ValidUtils.html, makeResponse(rows, program, language)));
		}catch(WrongComplexException e){}
	}
	private String makeResponse(List<String> rows, Program program,String language)
	{
		@SuppressWarnings("unused")
		HashMap<Integer, List<Error>> error = program.getAllErrors();
		String body = "";
		Map<Integer, List<Expression>> map = program.mapExpression();
		Expression prev = null;
		String actualTree = "";
		for(int i = 0; i < rows.size(); i++)
		{
			List<Expression> list = map.get(i+1);
			if(list != null)
				body += String.format(ValidUtils.row, i+1, ValidUtils.countSpace(rows.get(i)), ValidUtils.htmlValidReplace(rows.get(i)), list.get(0).hasErrors() ? "error" : "noError", ValidUtils.hasErrors(list) ? ValidUtils.prepareErrors(list, language) : ValidUtils.prepareExpressions(list, language));
			else
				body += String.format(ValidUtils.row, i+1, ValidUtils.countSpace(rows.get(i)), ValidUtils.htmlValidReplace(rows.get(i)), "plain", "plain");
		}
		return body;
	}

}