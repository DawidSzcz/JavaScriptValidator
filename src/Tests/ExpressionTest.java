package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import exception.WrongComplexException;
import expression.Program;
import parser.ExpressionParser;
import validator.Context;

@RunWith(Parameterized.class)
public class ExpressionTest {
	String url;
	String data;
	boolean result;
	public ExpressionTest(String u, String d, Boolean r) {
		url = u; data = d; result = r;
	}
	@Parameterized.Parameters
	public static Collection parametrs() throws IOException 
	{
		boolean bool;
		List<Object[]> parametrs = new LinkedList<Object[]>();
		File file = new File("testy");
		FileUtils.cleanDirectory(file);
		ExelReader.exelToTxt();
		String d;
		String[] urls = file.list();
		for(String url : urls)
		{
			Pattern pat = Pattern.compile("\\[true\\]");
			Matcher match = pat.matcher(url);
			if(match.find())
				bool=true;
			else
				bool=false;
			
			d = TestUtils.readFromFile("testy\\"+url);
			Object[] temp = {url, d, bool};
			parametrs.add(temp);
		}
		
		return parametrs;
	}

	@Test
	public void test() throws WrongComplexException
	{
		Context.clear();
		Program program = new Program(data);
		program.mapExpression();
		HashMap<Integer, List<enums.Error>> errors = program.getAllErrors();
		assertEquals(errors.size() == 0, result);
	}
}