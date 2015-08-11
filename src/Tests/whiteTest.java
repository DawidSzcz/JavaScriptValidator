package Tests;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.ParseUtils;

public class whiteTest {
	@Test 
	public void cleanWhiteTest()
	{
		assertEquals(ParseUtils.cleanWhite("a dsds \ta()() \nb n    n"), "a dsds a()() b n n ");
		assertEquals(ParseUtils.cleanWhite("das     \t\t\n dasdas \t   dasdasdad431 dasdas223233 "), "das dasdas dasdasdad431 dasdas223233 ");
	}
}
