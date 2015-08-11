package Tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import exception.InvalidOperator;

public class ValidationTest {

	@Test()
	public void testOperatorCprrect() throws IOException,  InvalidOperator {

		assertTrue(TestUtils.testStatement("vt_codes[vs_query.getString(stringID23424)] "));
		assertTrue(TestUtils.testStatement("vr_Row.getParameterByName(stringid13213).setValue(vt_codes[vr_Row.getParameterByName(stringid123).getValue()])"));
		assertTrue(TestUtils.testStatement("PARAM1 == StringID8219226471481774312 || ((VAL1 != null && PARAM1.equals(VAL1)) || (VAL2 != null && !PARAM1.equals(VAL2)))"));
		assertTrue(TestUtils.testStatement("b(a==1)"));
		assertTrue(TestUtils.testStatement("_TABLE.getRow(_TABLE.getRowCount()-2)"));
		assertTrue(TestUtils.testStatement("_TABLE.getRow(_TABLE.getRowCount()-as)"));
		assertFalse(TestUtils.testStatement("a =1"));
		assertTrue(TestUtils.testStatement("maskAndCaptionStyle[0] "));
		assertTrue(TestUtils.testStatement("vs_query.getString(StringID959569054)"));
		assertTrue(TestUtils.testStatement("var i+new as"));
		assertTrue(TestUtils.testStatement("stringid231321 ? _WFL_OP_VAL1.getValue() : null"));
		assertFalse(TestUtils.testStatement("w == 1 +++na"));
		assertFalse(TestUtils.testStatement("w == 1 ---na"));
		assertTrue(TestUtils.testStatement("w == 1 + ++na"));
		assertTrue(TestUtils.testStatement("w == 1 - --na"));
		assertFalse(TestUtils.testStatement("w = 1"));
		assertTrue(TestUtils.testStatement("((action==StringID8947072945635327574)||(action==StringID514299064289191436))"));
		assertFalse(TestUtils.testStatement("_WFL_OP_V_PARAM != (undefined-45645)321 ? _WFL_OP_V_PARAM.getValue()+12 : ++IS_UNDEFINED"));
		assertFalse(TestUtils.testStatement("rowp.getParameter(2).setValue(null)rowp.getParameter(3).setValue(null)"));
		assertTrue(TestUtils.testStatement("++_WFL_OP_V_PARAM != undefined ? _WFL_OP_V_PARAM.getValue() : IS_UNDEFINED"));
		assertTrue(TestUtils.testStatement("_WFL_OP_V_PARAM != undefined-45645 ? _WFL_OP_V_PARAM.getValue()+12 : ++IS_UNDEFINED"));
		assertTrue(TestUtils.testStatement("_WFL_OP_V_PARAM != (undefined-45645)*321 ? _WFL_OP_V_PARAM.getValue()+12 : ++IS_UNDEFINED"));
		assertTrue(TestUtils.testStatement("assertTrue(TestUtils.testStatement())"));
		assertTrue(TestUtils.testStatement("x().y() +1 "));
		assertTrue(TestUtils.testStatement("assertTrue(TestUtils.testStatement(cos))++"));
		assertTrue(TestUtils.testStatement(" cse "));
		assertTrue(TestUtils.testStatement("x + 1 "));
		assertTrue(TestUtils.testStatement("!2+54+32==3 "));
		assertTrue(TestUtils.testStatement("x - --p"));
		assertTrue(TestUtils.testStatement("x < 3 > 5"));
		assertTrue(TestUtils.testStatement("-x + 5"));
		assertTrue(TestUtils.testStatement("  !4  +  5"));
		assertTrue(TestUtils.testStatement("  !x  "));
		assertTrue(TestUtils.testStatement("!2+54+" + "\n" + "32==3 "));
		assertTrue(TestUtils.testStatement("453+(tr+494)"));
		assertTrue(TestUtils.testStatement("453+(tr+494)+dad[wda+13]"));
		assertTrue(TestUtils.testStatement("453+(tr+494)+dad[wda]"));
		assertTrue(TestUtils.testStatement("453+(tr+494+dad[wda+12])"));
		assertFalse(TestUtils.testStatement("[x]++"));
		assertFalse(TestUtils.testStatement("x = 1"));
		assertFalse(TestUtils.testStatement("--1"));
		assertFalse(TestUtils.testStatement("1 =(= 2)"));
		assertTrue(TestUtils.testStatement("1 == 2 +1 == 0"));
		assertFalse(TestUtils.testStatement("x()y()"));
		assertTrue(TestUtils.testStatement("x.y +1 "));
		assertTrue(TestUtils.testStatement(" java.math.BigInteger.valueOf(query.getString(cus))"));
		assertTrue(TestUtils.testStatement(" rowp.getParameter(0).setValue(null)"));
		assertTrue(TestUtils.testStatement("_logger.warn(asdas + process_id)"));
		assertTrue(TestUtils.testStatement("_logger.warn"));		
		assertFalse(TestUtils.testStatement(" x!"));
		assertFalse(TestUtils.testStatement("x++ w"));
		assertFalse(TestUtils.testStatement("x y+w"));
		assertFalse(TestUtils.testStatement("x - --1"));
		assertFalse(TestUtils.testStatement("_logger.warn(asdas + process_id"));
		assertFalse(TestUtils.testStatement(" rowp.getParameter(0) .setValue(null)"));
		assertFalse(TestUtils.testStatement("java.math.1.valueOf(query.getString(cus))"));
		assertTrue(TestUtils.testStatement("x(a,b)"));
		assertTrue(TestUtils.testStatement("x(x(a,b),b)"));
		assertFalse(TestUtils.testStatement("x((a,b), c)"));
		assertFalse(TestUtils.testStatement("x((a,b, c), c)"));
		assertTrue(TestUtils.testStatement("x(x(a,b, c),b, c(d,e))"));
		assertTrue(TestUtils.testStatement("JavaScriptException.javaException instanceof java.lang.NumberFormatException"));
		assertFalse(TestUtils.testStatement("JavaScriptException.javaException instanceof"));
		assertFalse(TestUtils.testStatement("JavaScriptException.javaException instanceofjava.lang.NumberFormatException"));
// to do		assertTrue(TestUtils.testStatement("x(x(a,b, c),b+(a+v), c(d,e))"));

	}
}
