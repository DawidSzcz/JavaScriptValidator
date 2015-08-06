package validator;

import java.util.ArrayList;
import java.util.List;

import Atoms.Statement;

public class Context {
	static public List<Statement> openPorts = new ArrayList<Statement>();
	static public List<String> variables = new ArrayList<String>();
	static public boolean variableWithUnderscoreValid = false;
	public static void clear(){
		openPorts.clear();
		variables.clear();
	}
}
