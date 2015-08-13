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
	public ControlExpression(String name, int currentLine, Map<String, StringContainer> strings, List<String> labels, String branch) {
		super(name, currentLine, strings);
		this.branch = branch;
		Matcher matchC = Patterns.control.matcher(name);
		Matcher matchL = Patterns.label.matcher(name);
		if(matchC.find())
			control = matchC.group();
		name = name.replace(control, "");
		if(!(name.matches("\\s*;?")))
		{
			label = ParseUtils.cleanLine(name);
			if(!labels.contains(label))
				this.addError(Error.MissingLabeDeclaration);
		}
		if(!(branch.contains("For") || branch.contains("While") || (control.matches("\\s*break") && branch.contains("Switch"))))
			this.addError(Error.ControlStatementNotInLoop);
	}



	@Override
	public String toString() {
		return branch + "Control statement";
	}

}
