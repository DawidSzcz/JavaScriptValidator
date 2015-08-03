package expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import parser.ParseUtils;
import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import exception.InvalidOperator;
import parser.Patterns;

public class SimpeExpresion extends Expression {

	// static boolean isPortOpen=false;
	static List<Statement> openPorts = new ArrayList<Statement>();

	public SimpeExpresion(String name, int currentLine, Map<String, StringContainer> strings) 
	{
		super(name, strings);
		line = currentLine + ParseUtils.getLinesBNS(name);
		area = ParseUtils.getLines(name) - ParseUtils.getLinesBNS(name);
	}

	protected void isPortOpen(Statement argument, List<Statement> variables) {
		Matcher matcherPortFunktion = Patterns.sqlGetPortFunction.matcher(argument.getName());
		if (matcherPortFunktion.find()) {
			openPorts.addAll(variables);
		}
	}

	// public void
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		Matcher matcherExecuteStetmentFunction = Patterns.sqlExecuteStetmentFunction.matcher(name);
		if (matcherExecuteStetmentFunction.find()) {
			String[] variableSplit = matcherExecuteStetmentFunction.group().split(".executeStatement");
			String variableExeciut = variableSplit[0];
			variableExeciut = ParseUtils.cleanLine(variableExeciut);
			for (Statement openPort : openPorts) {
				if (openPort.getName().equals(variableExeciut)) {
					return true;
				}
			}
			errors.add(Error.SqlPortIsNotOpen);
			return false;
		}
		return true;
	}
}
