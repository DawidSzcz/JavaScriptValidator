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
import expression.OperatorCorrect;

public class ValidationTest {

	@Test
	public void testOperatorCprrect() throws IOException, WrongWhileException {
		String input = "--toto+~saaa+troro++ != 23- p *ewrw--+ 9*ale--/876-!42";
		String output = OperatorCorrect.isOpreratorCorrect(input);

		assertTrue(output.compareTo("") == 0);

	}

	@Test
	public void testParser() throws IOException, WrongWhileException {
		String input = "";
		String line;
		try {
			FileReader file = new FileReader("daneDoTestow.txt");
			BufferedReader bufferedReader = new BufferedReader(file);
			while ((line = bufferedReader.readLine()) != null){
				input += line + "\n";
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {

		}

		List<String> test=Arrays.asList(input.split("\n"));
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
