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
	List<String> labels;
	public ControlExpression(String name, int currentLine, List<String> labels, String branch) {
		super(name, currentLine);
		this.branch = branch;
		this.labels = labels;
		Matcher matchC = Patterns.control.matcher(this.name);
		Matcher matchL = Patterns.label.matcher(this.name);
		if(matchC.find())
			control = matchC.group();
		this.name = this.name.replace(control, "");
	}
	
	@Override
	public boolean isValid() {
		boolean valid = true;
		if(!(branch.contains("For") || branch.contains("While") || (control.matches("\\s*break") && branch.contains("Switch"))))
		{
			this.addError(Error.ControlStatementNotInLoop, line);
			valid = false;
		}
		if(!(name.matches("\\s*;?")))
		{
			label = ParseUtils.cleanLine(name);
			if(!labels.contains(label))
			{
				this.addError(Error.MissingLabelDeclaration, line);
				valid = false;
			}
		}
		return valid;
	}

	@Override
	public String toString() {
		return branch + "Control statement";
	}

}
