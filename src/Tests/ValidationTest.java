package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import exception.InvalidOperator;
import exception.WrongWhileException;
import expression.Expression;
import expression.ExpressionParser;
import expression.If;
import expression.Invocation;

public class ValidationTest {

	@Test

	public void testOperatorCprrect() throws IOException, WrongWhileException, InvalidOperator {

		assertFalse(TestUtils.testStatement("_WFL_OP_V_PARAM != (undefined-45645)321 ? _WFL_OP_V_PARAM.getValue()+12 : ++IS_UNDEFINED"));
		assertTrue(TestUtils.testStatement("++_WFL_OP_V_PARAM != undefined ? _WFL_OP_V_PARAM.getValue() : IS_UNDEFINED"));
		assertTrue(TestUtils.testStatement("_WFL_OP_V_PARAM != undefined-45645 ? _WFL_OP_V_PARAM.getValue()+12 : ++IS_UNDEFINED"));
		assertTrue(TestUtils.testStatement("_WFL_OP_V_PARAM != (undefined-45645)*321 ? _WFL_OP_V_PARAM.getValue()+12 : ++IS_UNDEFINED"));
		assertTrue(TestUtils.testStatement("assertTrue(TestUtils.testStatement())"));
		assertTrue(TestUtils.testStatement("x().y() +1 "));
		assertTrue(TestUtils.testStatement("assertTrue(TestUtils.testStatement(cos))++"));
		assertTrue(TestUtils.testStatement(" cse "));
		assertTrue(TestUtils.testStatement("x + 1 "));
		assertTrue(TestUtils.testStatement("!2+54+32==3 "));
		assertTrue(TestUtils.testStatement("x - --p"));
		assertTrue(TestUtils.testStatement("x < 3 > 5"));
		assertTrue(TestUtils.testStatement("-x + 5"));
		assertTrue(TestUtils.testStatement("  !4  +  5"));
		assertTrue(TestUtils.testStatement("  !x  "));
		assertTrue(TestUtils.testStatement("!2+54+" + "\n" + "32==3 "));
		assertTrue(TestUtils.testStatement("453+(tr+494)"));
		assertTrue(TestUtils.testStatement("453+(tr+494)+dad[wda+13]"));
		assertTrue(TestUtils.testStatement("453+(tr+494)+dad[wda]"));
		assertTrue(TestUtils.testStatement("453+(tr+494+dad[wda+12])"));
		assertFalse(TestUtils.testStatement("[x]++"));
		assertFalse(TestUtils.testStatement("x = 1"));
		assertFalse(TestUtils.testStatement("--1"));
		assertFalse(TestUtils.testStatement("1 =(= 2)"));
		assertTrue(TestUtils.testStatement("1 == 2 +1 == 0"));
		assertFalse(TestUtils.testStatement("x()y()"));
		assertTrue(TestUtils.testStatement("x.y +1 "));
		assertTrue(TestUtils.testStatement(" java.math.BigInteger.valueOf(query.getString(cus))"));
		assertTrue(TestUtils.testStatement(" rowp.getParameter(0).setValue(null)"));
		assertTrue(TestUtils.testStatement("_logger.warn(asdas + process_id)"));
		assertTrue(TestUtils.testStatement("_logger.warn"));		
		assertFalse(TestUtils.testStatement(" x!"));
		assertFalse(TestUtils.testStatement("x++ w"));
		assertFalse(TestUtils.testStatement("x y+w"));
		assertFalse(TestUtils.testStatement("x - --1"));
		assertFalse(TestUtils.testStatement("_logger.warn(asdas + process_id"));
		assertFalse(TestUtils.testStatement(" rowp.getParameter(0) .setValue(null)"));
		assertFalse(TestUtils.testStatement("java.math.1.valueOf(query.getString(cus))"));
		assertTrue(TestUtils.testStatement("x(a,b)"));
		assertTrue(TestUtils.testStatement("x(x(a,b),b)"));
		assertFalse(TestUtils.testStatement("x((a,b), c)"));
		assertFalse(TestUtils.testStatement("x((a,b, c), c)"));
		assertTrue(TestUtils.testStatement("x(x(a,b, c),b, c(d,e))"));
// to do		assertTrue(TestUtils.testStatement("x(x(a,b, c),b+(a+v), c(d,e))"));

	}

	@Test
	public void testParser() throws IOException, WrongWhileException {
		String input = "";
		String line;
		try {
			FileReader file = new FileReader("daneDoTestow.txt");
			BufferedReader bufferedReader = new BufferedReader(file);
			while ((line = bufferedReader.readLine()) != null) {
				input += line + "\n";
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {

		}

		List<String> test = Arrays.asList(input.split("\n"));
		assertSame(5, test.size());
	}

	@Test
	public void test3() throws IOException {
		String input = "if (rowid.equals(rowp.getParameter(0).getValue()))\n" + "{\n" + "	-eter(0).setValue(null);\n"
				+ "}" + "rowp.getParameter(0).setValue(null);\n";
		ExpressionParser parser = new ExpressionParser(input);
		Expression program = parser.parse(input);
		assertTrue(program.get(0) instanceof If);
		assertTrue(program.get(1) instanceof Invocation);
		assertTrue(program.get(0).get(1) instanceof Invocation);
	}
}
