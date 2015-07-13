package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

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
import expression.OperatorCorrect;

public class ValidationTest {

//	@Test
//	public void test() throws IOException, WrongWhileException {
////		String input = "--toto+troro++ = 23- p *ewrw--+ 9*ale--";
//		String input = "totot!== 534-10";
//		String output = OperatorCorrect.isOpreratorCorrect(input);
//
//		assertTrue(output.compareTo("") == 0);
//		// ExpressionParser parser = new ExpressionParser();
//		// List<Expression> list = parser.Parse(input);
//		// assertSame(3, list.size());
//		// assertTrue(list.get(0) instanceof If);
//		// assertTrue(list.get(1) instanceof Invocation);
//	}

	@Test
	public void test2() throws IOException, WrongWhileException {
/*		String input = "process_id=_featureManager.getProcessInstanceFeature().getWFLIProcessId(); \n"
				+ "if ((_COUNTRY_ID.getValue()==320)||(_COUNTRY_ID.getValue()==390)) \n" + "{\n"
				+ "	dbPort = _featureManager.getTytanDBPortFeature(); \n"
				+ "	query = dbPort.executeStatement(\"select NOORDER, RESITEM_ID from table (xpwflcitem.fcGetMSISDNrow(\"+process_id+\"))\"); \n"
				+ "	while(query.next()) \n" + "	{ \n"
				+ "		noorder = java.math.BigInteger(query.getString(\"NOORDER\")); \n"
				+ "		resitem = java.math.BigInteger(query.getString(\"RESITEM_ID\")); \n"
				+ "		_TABLE.getRow(noorder).getParameter(5).setValue(resitem); \n" + "	} \n" + "}\n";
		ExpressionParser parser = new ExpressionParser();
		List<Expression> list = parser.parse(input);

		assertSame(3, list.size());
		assertTrue(list.get(1) instanceof If);
		// assertTrue(list.get(1) instanceof Invocation); */
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
	@Test
	public void testOperators()
	{
		assertTrue(OperatorCorrect.isOpreratorCorrect("x + 1 "));
		assertFalse(OperatorCorrect.isOpreratorCorrect("y = x + 1 "));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x --y"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x - --p"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("x < 3 > 5"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("x = 4 = 5"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("!4 + 5"));
		assertTrue(OperatorCorrect.isOpreratorCorrect("!x"));
		assertFalse(OperatorCorrect.isOpreratorCorrect("3 + 5 + 4 = 2"));
	}
}
