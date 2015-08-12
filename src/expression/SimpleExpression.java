package expression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import parser.ParseUtils;
import Atoms.Statement;
import Atoms.StringContainer;
import enums.Error;
import parser.Patterns;

public abstract class SimpleExpression extends Expression {

	public SimpleExpression(String name, int currentLine, Map<String, StringContainer> strings) 
	{
		super(name, strings);
		line = currentLine + ParseUtils.getLinesBNS(name);
		area = ParseUtils.getArea(name); // stare : ParseUtils.getLines(name) - ParseUtils.getLinesBNS(name);
	}

	protected void isPortOpen(Statement argument, List<Statement> variables) {
		variables.get(variables.size()-1).setName(variables.get(variables.size()-1).getName().replaceAll("var\\s*", ""));
		Matcher matcherPortFunktion = Patterns.sqlGetPortFunction.matcher(argument.getName());
		if (matcherPortFunktion.find()) {
			validator.Context.openPorts.addAll(variables);
		}
	}

	@Override
	public boolean isValid() {
		Matcher matcherExecuteStetmentFunction = Patterns.sqlExecuteStetmentFunction.matcher(name);
		if (matcherExecuteStetmentFunction.find()) {
			String[] variableSplit = matcherExecuteStetmentFunction.group().split(".executeStatement");
			String variableExeciut = variableSplit[0];
			variableExeciut = ParseUtils.cleanLine(variableExeciut);
			for (Statement openPort : validator.Context.openPorts) {
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
