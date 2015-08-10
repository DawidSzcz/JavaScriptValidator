package validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Atoms.Statement;
import parser.ExpressionParser;

public class Context {
	static public List<Statement> openPorts = new ArrayList<Statement>();
	static public List<String> variables = new ArrayList<String>();
	static public HashMap <String,List<Integer>> functions = new HashMap <String,List<Integer>>();
	static public List<String> functionsBehindDot =new ArrayList<String>();
	static public List<String> oneArgumentFunctions =new ArrayList<String>();
	static public List<String> zeroArgumentFunctions =new ArrayList<String>();
	static public boolean variableWithUnderscoreValid = false;
	static public ExpressionParser expressionParser;
	public static void clear(){
		openPorts.clear();
		variables.clear();
		functions.clear();
		functionsBehindDot.clear();
		oneArgumentFunctions.clear();
		zeroArgumentFunctions.clear();
		prepareFunctionBehindDot();
		prepareZeroArgumentFunctions();
		prepareOneArgumentFunctions();
		expressionParser = null;
	}
	private static void prepareFunctionBehindDot() {
		functionsBehindDot.add("setErrorMsg");
		functionsBehindDot.add("getValue");
		functionsBehindDot.add("getName");
		functionsBehindDot.add("getString");
		functionsBehindDot.add("getInt");
		functionsBehindDot.add("getParameterByName");
		functionsBehindDot.add("setValue");
		functionsBehindDot.add("setStringValue");
		functionsBehindDot.add("setIntegerValue");
		functionsBehindDot.add("setIntValue");
		functionsBehindDot.add("getRowsIterator");
	}
	public static void prepareZeroArgumentFunctions(){
		zeroArgumentFunctions.add("getRowsIterator");
	}
	public static void prepareOneArgumentFunctions(){
		oneArgumentFunctions.add("setErrorMsg");
		oneArgumentFunctions.add("getString");
		oneArgumentFunctions.add("getInt");
		oneArgumentFunctions.add("getParameterByName");
		oneArgumentFunctions.add("setValue");
		oneArgumentFunctions.add("setStringValue");
		oneArgumentFunctions.add("setIntegerValue");
		oneArgumentFunctions.add("setIntValue");
	}

}
