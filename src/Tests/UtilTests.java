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
		Pair p = ParseUtils.findCondition("if", input1);
		assertEquals("a", p.getKey());
		assertEquals("{b;}", p.getValue());
	}
}
