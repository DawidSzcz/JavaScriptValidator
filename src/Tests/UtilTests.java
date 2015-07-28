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

import enums.Instruction;
import exception.WrongComplexException;
import javafx.util.Pair;
import parser.ParseUtils;
import parser.ParseUtils.Triple;

public class UtilTests 
{
	@Test
	public void testComplexExpressionDivision() throws WrongComplexException
	{
		String input1 = "if(a){b;}";
		String input2 = "for(1; 2; 3)\n{a; b; c;}";
		String input3 = "if(a){\nb;\n}\n}\n";
		Pair p1 = ParseUtils.splitBlock(Instruction.IF, input1);
		Pair p2 = ParseUtils.splitBlock(Instruction.FOR, input2);
		Pair<String, String> p3 = ParseUtils.splitBlock(Instruction.IF, input3);
		assertEquals("a", p1.getKey());
		assertEquals("b;", p1.getValue());
		assertEquals("1; 2; 3", p2.getKey());
		assertEquals("a; b; c;", p2.getValue());
		assertEquals("a", p3.getKey());
		assertEquals("\nb;\n}\n", p3.getValue());
	}
	@Test
	public void commentsAndStrings() 
	{
		Triple trip =ParseUtils.removeStrAndCom("//ssssdasdas");
		assertEquals(trip.comments.size(), 1);
		assertEquals(trip.strings.size(), 0);
		Triple trip2 =ParseUtils.removeStrAndCom("//ssssdas\"as");
		Triple trip3 =ParseUtils.removeStrAndCom("\"dasda'dasd'dasdasd\"");
		assertEquals(trip2.comments.size(), 1);
		assertEquals(trip2.strings.size(), 0);
		assertEquals(trip3.comments.size(), 0);
		assertEquals(trip3.strings.size(), 1);
	}
}
