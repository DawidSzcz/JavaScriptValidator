package validator;

import java.util.ArrayList;
import java.util.List;
import Atoms.Statement;
import parser.ExpressionParser;

public class Context {
	static public List<Statement> openPorts = new ArrayList<Statement>();
	static public List<String> variables = new ArrayList<String>();
	static public boolean variableWithUnderscoreValid = false;
	static public ExpressionParser expressionParser;
	public static void clear(){
		openPorts.clear();
		variables.clear();
		expressionParser = null;
	}
}
