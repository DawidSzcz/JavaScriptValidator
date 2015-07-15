package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
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

		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("assertTrue(operator.OperatorCorrect.isOpreratorCorrect(cos))"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect(" cse "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x + 1 "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("!2+54+32==3 "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x - --p"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x < 3 > 5"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("  !4  +  5"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("  !x  "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("!2+54+" + "\n" + "32==3 "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("453+(tr+494)"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("453+(tr+494)+dad[wda+13]"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("453+(tr+494)+dad[wda]"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("453+(tr+494+dad[wda+12])"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("[x]++"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x = 1"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("--1"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("1 =(= 2)"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("1 == 2 +1 == 0"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x()y()"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x.y +1 "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect(" java.math.BigInteger.valueOf(query.getString(cus))"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect(" rowp.getParameter(0).setValue(null)"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("_logger.warn(asdas + process_id)"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("_logger.warn"));		
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect(" x!"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x++ w"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x y+w"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x - --1"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("_logger.warn(asdas + process_id"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect(" rowp.getParameter(0) .setValue(null)"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("java.math.1.valueOf(query.getString(cus))"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("assertTrue(operator.OperatorCorrect.isOpreratorCorrect(cos))"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x().y() +1 "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x(a,b)"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x(x(a,b),b)"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x((a,b), c)"));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x((a,b, c), c)"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x(x(a,b, c),b, c(d,e))"));
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
		List<Expression> list = parser.parse(input);
		assertSame(2, list.size());
		assertTrue(list.get(0) instanceof If);
		assertTrue(list.get(1) instanceof Invocation);
		assertTrue(list.get(0).get(1) instanceof Invocation);
	}
}
