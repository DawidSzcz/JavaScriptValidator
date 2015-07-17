
package Tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;

import expression.ExpressionParser;
import expression.Patterns;


@RunWith(Parameterized.class)
public class ExpressionsTest {

	String data;
	boolean result;
	public ExpressionsTest(String d, Boolean r) {
		data = d; result = r;
	}
	@Parameterized.Parameters
	public static Collection parametrs() throws IOException 
	{
		List<Object[]> parametrs = new LinkedList<Object[]>();
		File file = new File("testy");
		FileUtils.cleanDirectory(file);
		ExelReader.exelToTxt();
		String d;
		String[] urls = file.list();
		for(String url : urls)
		{
			Pattern pat = Pattern.compile("\\[.*\\]");
			Matcher match = pat.matcher(url);
			match.find();
			String b = match.group();
			Boolean bool = Boolean.parseBoolean(b.substring(1, 5));
			d = TestUtils.readFromFile("testy\\"+url);
			Object[] temp = {d, bool};
			parametrs.add(temp);
		}
		
		return parametrs;
	}
	@Test
	public void test() throws IOException 
	{
		ExpressionParser parser = new ExpressionParser(data);
		assertEquals(parser.parse().isValid(), result);
	}

}

