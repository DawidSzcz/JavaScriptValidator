package Tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import exception.WrongWhileException;
import expression.Expression;
import expression.ExpressionParser;
import expression.If;
import expression.Invocation;
import expression.OperatorCorrect;

public class ValidationTest {

	@Test
public void test() throws IOException, WrongWhileException {
		String input = "troro++=23";
		String output = OperatorCorrect.isOpreratorCorrect(input);
		
		assertTrue(output.compareTo("")==0);
//		ExpressionParser parser = new ExpressionParser();
//		List<Expression> list = parser.Parse(input);
//		assertSame(3, list.size());
//		assertTrue(list.get(0) instanceof If);
//		assertTrue(list.get(1) instanceof Invocation);
	}

}
