package Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runners.Parameterized;

import Atoms.StringContainer;
import enums.Instruction;
import exception.WrongAssignmentException;
import exception.WrongComplexException;
import expression.Assignment;
import expression.Invocation;
import javafx.util.Pair;
import operator.Patterns;
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
	public void removingblock() 
	{
		Pair<String, HashMap<String, String>>trip =ParseUtils.removeBlocks("else{dasdasda}");
		assertEquals(trip.getValue().size(), 1);
		assertTrue(trip.getKey().matches("^BlockID\\d+;$"));
		Pair<String, HashMap<String, String>> trip2=ParseUtils.removeBlocks("if(ss){dasdasd}dad");
		assertEquals(trip2.getValue().size(), 1);
		assertTrue(trip2.getKey().matches("^BlockID\\d+;dad$"));
		Pair<String, HashMap<String, String>> trip3 =ParseUtils.removeBlocks("if(ss)\n{dasdasd}\ndad");
		assertEquals(trip3.getValue().size(), 1);
		assertTrue(trip3.getKey().matches("^BlockID\\d+;\ndad$"));
		Pair<String, HashMap<String, String>> trip4 =ParseUtils.removeBlocks("\n\nif(sss)\n\n  \t{\t\nelse{das}d\n}da\nd");
		assertEquals(trip4.getValue().size(), 2);
		assertTrue(trip4.getKey().matches("^\n\nBlockID\\d+;da\nd$"));
	}
	@Test
	public void commentsAndStringsMarek() 
	{
		Pair<String, HashMap<String, StringContainer>>trip =ParseUtils.takeOutStringsAndComents("//ssssdasdas\ndsads");
		assertEquals(trip.getKey(), "\ndsads");
		Pair<String, HashMap<String, StringContainer>> trip2=ParseUtils.takeOutStringsAndComents("//ssssdas\"as");
		assertEquals(trip2.getKey(), "\n");
		assertEquals(trip2.getValue().size(), 0);
		Pair<String, HashMap<String, StringContainer>> trip3 =ParseUtils.takeOutStringsAndComents("\"dasda'dasd'dasdasd\"");
		assertTrue(trip3.getKey().matches("^StringID\\d+$"));
		assertEquals(trip3.getValue().size(), 1);
		Pair<String, HashMap<String, StringContainer>> trip4 =ParseUtils.takeOutStringsAndComents("/*dasd\n\nsd//as\n*/ds/* dsad */ada");
		assertTrue(trip4.getKey().matches("^\n\n\ndsada$"));
		assertEquals(trip4.getValue().size(), 0);
		Pair<String, HashMap<String, StringContainer>> trip5 =ParseUtils.takeOutStringsAndComents("dasd \" asdasdasdasdadasd");
//		assertTrue(trip5.getValue().get(0).getErrors()));
		assertEquals(trip5.getValue().size(), 1);
		Pair<String, HashMap<String, StringContainer>> trip6 =ParseUtils.takeOutStringsAndComents("dasd \" asdasd\\s\\rdasdadasd");
//		for(String key :trip6.getValue().keySet()){
//			assertEquals(trip6.getValue().get(key).errors.size(),2);
//			assertEquals(trip6.getValue().get(key).errors.get(0), enums.Error.InvalidEscape);
//			assertEquals(trip6.getValue().get(key).errors.get(1), enums.Error.EnterInString);
//		}

	}
	@Test
	public void sqlValidationTest(){
		Map<String, StringContainer>smap=null;
		
		String AssigString ="dbProt= dupa = _featureManager.getTytanDBPortFeature()";
		String AssigString2 ="query = dbProt.executeStatement(strSQL)";
		String AssigString3 ="";
		try {
			Assignment Assig= new Assignment(AssigString,0,smap);
			Assignment Assig2= new Assignment(AssigString2,1,smap);
//			Assignment Assig3= new Assignment(AssigString3,2,smap);
			assertTrue(Assig.isValid());
			assertTrue(Assig2.isValid());
//			assertTrue(Assig3.isValid());
		} catch (WrongAssignmentException e) {
			e.printStackTrace();
		}
//		String expresion0="addsadsas";
//		String expresion1="dbPort.executeStatement(strSQL)";
//		String expresion2="_featureManager.getTytanDBPortFeature()";
//		String expresion3="dbPort.executeStatement(strSQL)";
//		Invocation invocation0 = new Invocation(expresion0,0,smap);
//		Invocation invocation1 = new Invocation(expresion1,1,smap);
//		Invocation invocation2 = new Invocation(expresion2,2,smap);
//		Invocation invocation3 = new Invocation(expresion3,3,smap);
//		assertTrue(invocation0.isValid());
//		assertFalse(invocation1.isValid());
//		assertTrue(invocation2.isValid());
//		assertTrue(invocation3.isValid());
		

		
	}
}
