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
	static public boolean variableWithUnderscoreValid = false;
	static public ExpressionParser expressionParser;
	public static void clear(){
		openPorts.clear();
		variables.clear();
		functions.clear();
		expressionParser = null;
	}
}
