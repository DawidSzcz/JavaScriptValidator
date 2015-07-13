package Tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

import enums.Error;

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
import expression.Program;
import operator.OperatorCorrect;
import validator.ValidUtils;

public class ValidationTest {

	@Test
	public void testOperatorCprrect() throws IOException, WrongWhileException {

		assertTrue(operator.OperatorCorrect.isOpreratorCorrect(" cse "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x + 1 "));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("!2+54+32==3 "));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x y+w"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x - --p"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("x < 3 > 5"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("  !4  +  5"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("  !x  "));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect(" x!"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("!2+54+" + "\n" + "32==3 "));
		assertFalse(operator.OperatorCorrect.isOpreratorCorrect("x++ w"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("453+(tr+494)"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("453+(tr+494)+dad[wda]"));
		assertTrue(operator.OperatorCorrect.isOpreratorCorrect("453+(tr+494+dad[wda+12])"));
	}

	
	@Test
	public void testParser() throws IOException, WrongWhileException {
		String input = TestUtils.readFromFile("daneDoTestow.txt");
		List<String> test = Arrays.asList(input.split("\n"));
		assertSame(5, test.size());
	}

	@Test
	public void test3() throws IOException {
		String input = "if (rowid.equals(rowp.getParameter(0).getValue()))\n" 
				+ "{\n" 
				+ "	-eter(0).setValue(null);\n"
				+ "}" 
				+ "rowp.getParameter(0).setValue(null);\n";
		ExpressionParser parser = new ExpressionParser();
		List<Expression> list = parser.parse(input);
		Program program = new Program("Program", list);
		HashMap<Integer, List<Error>> errors = program.getAllErrors();
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
