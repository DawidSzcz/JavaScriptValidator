package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runners.Parameterized;

import exception.WrongComplexException;
import javafx.util.Pair;
import parser.ParseUtils;

public class UtilTests 
{
	@Test
	public void testComplexExpressionDivision() throws WrongComplexException
	{
		String input1 = "if(a){b;}";
		String input2 = "for(1; 2; 3)\n{a; b; c;}";
		String input3 = "if(a){\nb;\n}\n}\n";
		Pair p1 = ParseUtils.findCondition("if", input1);
		Pair p2 = ParseUtils.findCondition("for", input2);
		Pair<String, String> p3 = ParseUtils.findCondition("if", input3);
		assertEquals("a", p1.getKey());
		assertEquals("b;", p1.getValue());
		assertEquals("1; 2; 3", p2.getKey());
		assertEquals("a; b; c;", p2.getValue());
		assertEquals("a", p3.getKey());
		assertEquals("\nb;\n}\n", p3.getValue());
	}
}
