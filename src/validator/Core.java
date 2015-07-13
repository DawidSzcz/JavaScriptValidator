package validator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import expression.Expression;
import expression.ExpressionIterator;
import expression.ExpressionParser;

public class Core extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		ExpressionParser parser = new ExpressionParser();

		boolean test =operator.OperatorCorrect.isOpreratorCorrect(request.getParameter("javaScript"));
		List<Expression> list = parser.parse(request.getParameter("javaScript"));
		List<String> rows = Arrays.asList(request.getParameter("javaScript").split("\n"));
		out.println(String.format(ValidUtils.html, makeResponse(rows, list)));
	}
	private String htmlValidReplace(String javaScirptText) {
		javaScirptText=javaScirptText.replace("&", "&#x26;");
		javaScirptText=javaScirptText.replace("<", "&#x3C;");
		javaScirptText=javaScirptText.replace(">", "&#x3E;" );
		javaScirptText=javaScirptText.replace("\"", "&#x22;");
		return javaScirptText;
	}
	private String makeResponse(List<String> rows, List<Expression> list)
	{
		String body = "";
		ExpressionIterator iterator = new ExpressionIterator(list);
		Expression next = iterator.next();
		for(int i = 0; i < rows.size(); i++)
		{
			if(rows.get(i).contains(next.getName()))
			{
				body += String.format(ValidUtils.row, i, ValidUtils.countSpace(htmlValidReplace(rows.get(i))), rows.get(i), next.hasErrors() ? "error" : "noError", next.hasErrors() ? makeData(next) : iterator.getTree());
				next = iterator.next();
			}
			else
				body += String.format(ValidUtils.row, i, ValidUtils.countSpace(htmlValidReplace(rows.get(i))), rows.get(i), "plain", "plain");
		}
		return body;
	}
	private String makeData(Expression exp)
	{
		List<String> errors = exp.getErrors();
		if(errors.size() == 1)
			return errors.get(0);
		else
		{
			String data = "<select>\n";
			for(String message : errors)
				data += "<option>\n" + message + "</option>";
			return data + "\n</select>";
		}
	}

} 