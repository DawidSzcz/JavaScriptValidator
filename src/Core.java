import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Core extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		ArrayList<String> listaFunkcji = new ArrayList<String>( ValidationM.group(request.getParameter("javaScript")));

		for(int i=0; i<listaFunkcji.size() ; i++){
			out.print(listaFunkcji.get(i));
			out.print("\n \n \n");
		}
		
		
		
		
		//ArrayList<String> row = new ArrayList<String>(Arrays.asList(request.getParameter("first_name").split("\n")));
		//out.print(request.getParameter("first_name")+"\n");
		//out.print(ValidationM.comentaryVariable(row));
		
	}
}