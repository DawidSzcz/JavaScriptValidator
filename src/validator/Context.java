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
	static public boolean variableWithUnderscoreValid = false;
	public static void clear(){
		openPorts.clear();
		variables.clear();
		functions.clear();
		prepareFunctions();
		functionsBehindDot.clear();
		prepareFunctionBehindDot();
	}
	private static void prepareFunctions() {
		List<Integer> numberOfArguments = new ArrayList<Integer>();
		numberOfArguments.clear() ;
		numberOfArguments.add(0);
		functions.put("getRowsIterator",numberOfArguments);
		numberOfArguments = new ArrayList<Integer>();
		numberOfArguments.clear();
		numberOfArguments.add(1);
		functions.put("setErrorMsg",numberOfArguments);
		functions.put("getString",numberOfArguments);
		functions.put("getInt",numberOfArguments);
		functions.put("getParameterByName",numberOfArguments);
		functions.put("setStringValue",numberOfArguments);
		functions.put("setIntegerValue",numberOfArguments);
		functions.put("setIntValue",numberOfArguments);
		numberOfArguments = new ArrayList<Integer>();
		numberOfArguments.clear();
		numberOfArguments.add(1);
		numberOfArguments.add(2);
		functions.put("setValue",numberOfArguments);
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

}
