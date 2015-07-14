package Tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import org.junit.Test;

import exception.WrongComplexException;
import exception.WrongWhileException;
import expression.Expression;
import expression.ExpressionParser;
import expression.If;
import expression.Invocation;


public class ValidationTest {

	@Test
	public void testOperatorCprrect() throws IOException, WrongWhileException {

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
		/*
		 * String input =
		 * "process_id=_featureManager.getProcessInstanceFeature().getWFLIProcessId(); \n"
		 * +
		 * "if ((_COUNTRY_ID.getValue()==320)||(_COUNTRY_ID.getValue()==390)) \n"
		 * + "{\n" + "	dbPort = _featureManager.getTytanDBPortFeature(); \n" +
		 * "	query = dbPort.executeStatement(\"select NOORDER, RESITEM_ID from table (xpwflcitem.fcGetMSISDNrow(\"+process_id+\"))\"); \n"
		 * + "	while(query.next()) \n" + "	{ \n" +
		 * "		noorder = java.math.BigInteger(query.getString(\"NOORDER\")); \n"
		 * +
		 * "		resitem = java.math.BigInteger(query.getString(\"RESITEM_ID\")); \n"
		 * +
		 * "		_TABLE.getRow(noorder).getParameter(5).setValue(resitem); \n"
		 * + "	} \n" + "}\n"; ExpressionParser parser = new ExpressionParser();
		 * List<Expression> list = parser.parse(input);
		 * 
		 * assertSame(3, list.size()); assertTrue(list.get(1) instanceof If);
		 */
	}

	@Test
	public void test3() throws IOException {
		String input = "if (rowid.equals(rowp.getParameter(0).getValue()))\n" + "{\n" + "	-eter(0).setValue(null);\n"
				+ "}" + "rowp.getParameter(0).setValue(null);\n";
		ExpressionParser parser = new ExpressionParser();
		List<Expression> list = parser.parse(input);
		assertSame(2, list.size());
		assertTrue(list.get(0) instanceof If);
		assertTrue(list.get(1) instanceof Invocation);
		assertTrue(list.get(0).get(1) instanceof Invocation);
	}
}
