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
import operator.OperatorCorrect;

public class ValidationTest {

	@Test
	public void testOperatorCprrect() throws IOException, WrongWhileException, InvalidOperator {
		assertTrue(OperatorCorrect.isOpreratorCorrect("assertTrue(OperatorCorrect.isOpreratorCorrect(cos))"));
		assertTrue(OperatorCorrect.isOpreratorCorrect(" cse "));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x + 1 "));
		assertTrue(OperatorCorrect.isOpreratorCorrect("!2+54+32==3 "));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x - --p"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x < 3 > 5"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("  !4  +  5"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("  !x  "));
		assertTrue(OperatorCorrect.isOpreratorCorrect("!2+54+" + "\n" + "32==3 "));
		assertTrue(OperatorCorrect.isOpreratorCorrect("453+(tr+494)"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("453+(tr+494)+dad[wda+13]"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("453+(tr+494)+dad[wda]"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("453+(tr+494+dad[wda+12])"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("[x]++"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x = 1"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("--1"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("1 =(= 2)"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("1 == 2 +1 == 0"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x()y()"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x.y +1 "));
		assertTrue(OperatorCorrect.isOpreratorCorrect(" java.math.BigInteger.valueOf(query.getString(cus))"));
		assertTrue(OperatorCorrect.isOpreratorCorrect(" rowp.getParameter(0).setValue(null)"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("_logger.warn(asdas + process_id)"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("_logger.warn"));		
		assertFalse(OperatorCorrect.isOpreratorCorrect(" x!"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x++ w"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x y+w"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x - --1"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("_logger.warn(asdas + process_id"));
		assertFalse(OperatorCorrect.isOpreratorCorrect(" rowp.getParameter(0) .setValue(null)"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("java.math.1.valueOf(query.getString(cus))"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("assertTrue(OperatorCorrect.isOpreratorCorrect(cos))"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x().y() +1 "));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x(a,b)"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x(x(a,b),b)"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x((a,b), c)"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x((a,b, c), c)"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x(x(a,b, c),b, c(d,e))"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x(x(a,b, c), b, c(d,e))"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x!x"));
		assertTrue(OperatorCorrect.isOpreratorCorrect(" !x == !y"));
		
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
