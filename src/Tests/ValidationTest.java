package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import validator.*;

import org.junit.Test;

import exception.WrongWhileException;
import expression.Expression;
import expression.ExpressionParser;
import expression.If;
import expression.Invocation;

public class ValidationTest {

	@Test
	public void test() throws IOException, WrongWhileException 
	{
		String input = "if (rowid.equals(rowp.getParameter(0).getValue()))\n"+
                "{\n"+
                "	-eter(0).setValue(null);\n"+
                "}"+
                "rowp.getParameter(0).setValue(null);\n";
		ExpressionParser parser = new ExpressionParser();
		List<Expression> list = parser.parse(input);
		assertSame(2, list.size());
		assertTrue(list.get(0) instanceof If);
		assertTrue(list.get(1) instanceof Invocation);
		assertTrue(list.get(0).get(1) instanceof Invocation);
	}

}
