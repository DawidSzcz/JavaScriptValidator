package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

import expression.Expression;
import expression.ExpressionParser;

public class ExpressionsTest {

	@Test
	public void test() throws IOException {
		//ExelReader.exelToTxt();
		File file = new File("testy");
		ExpressionParser parser;
		String data;
		for(String url : file.list())
		{
			data = TestUtils.readFromFile("testy\\"+url);
			parser = new ExpressionParser(data);
			Expression prog = parser.parse(data);
			assertTrue(prog.isValid());
			System.out.println(url);
		}
	}

}
