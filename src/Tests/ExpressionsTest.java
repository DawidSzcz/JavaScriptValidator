package Tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Parameterized;

import expression.ExpressionParser;


@RunWith(Parameterized.class)
public class ExpressionsTest {

	String data;
	boolean result;
	public ExpressionsTest(String d, Boolean r) {
		data = d; result = r;
	}
	@Parameterized.Parameters
	public static Collection primeNumbers() throws IOException 
	{
		List<Object[]> parametrs = new LinkedList<Object[]>();
		File file = new File("testy");
		FileUtils.cleanDirectory(file);
		ExelReader.exelToTxt();
		String d;
		String[] urls = file.list();
		for(String url : urls)
		{
			d = TestUtils.readFromFile("testy\\"+url);
			Object[] temp = {d, true};
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
	public static void main(String[] args) {
	      Result result = JUnitCore.runClasses(ExpressionsTest.class);
	      for (Failure failure : result.getFailures()) {
	         System.out.println(failure.toString());
	      }
	      System.out.println(result.wasSuccessful());
	   }

}
