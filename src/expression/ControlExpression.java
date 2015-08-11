package expression;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Error;
import parser.ParseUtils;
import parser.Patterns;

public class ControlExpression extends SimpleExpression {

	String label;
	String control;
	public ControlExpression(String name, int currentLine, Map<String, StringContainer> strings, List<String> labels) {
		super(name, currentLine, strings);
		Matcher match = Patterns.control.matcher(name);
		if(match.find())
			control = match.group();
		name = name.replace(control, "");
		label = ParseUtils.cleanLine(name);
		if(!labels.contains(label))
			this.addError(Error.MissingLabeDeclaration);
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "Control statement";
	}

}
