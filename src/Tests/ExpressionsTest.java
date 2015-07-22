
package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import expression.ExpressionParser;


@RunWith(Parameterized.class)
public class ExpressionsTest {
	String url;
	String data;
	boolean result;
	public ExpressionsTest(String u, String d, Boolean r) {
		data = d; result = r; url=u;
	}
	@Parameterized.Parameters
	public static Collection parametrs() throws IOException 
	{
		boolean bool;
		List<Object[]> parametrs = new LinkedList<Object[]>();
		File file = new File("testy");
		FileUtils.cleanDirectory(file);
		ExelReader.exelToTxt();
		String data;
		String[] urls = file.list();
		for(String url : urls)
		{
			Pattern pat = Pattern.compile("\\[true\\]");
			Matcher match = pat.matcher(url);
			if(match.find())
				bool=true;
			else
				bool=false;
			

			data = TestUtils.readFromFile("testy\\"+url);
			Object[] temp = {url, data, bool};

			parametrs.add(temp);
		}
		
		return parametrs;
	}
	@Test
	public void test() throws IOException
	{
		ExpressionParser parser = new ExpressionParser(data);
		boolean x = parser.parse().getAllErrors().size() == 0;

		assertEquals(x, result);
	}

}


