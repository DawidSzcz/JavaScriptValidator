package expression;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Instruction;
import exception.InvalidFunction;
import exception.InvalidOperator;
import exception.WrongComplexException;
import parser.Patterns;
import validator.Context;

public class Switch extends ComplexExpression {
	public Switch(String statement, int currentLine, Map<String, StringContainer> strings, List<String> labels, String branch) {
		super(statement, Instruction.SWITCH, currentLine, strings, labels, branch + "Switch");
	}
	@Override
	public void splitBlock(Instruction instruction, int currentLine, String in, List<String> labels) throws WrongComplexException {
		super.splitBlock(instruction, currentLine, in, labels);
		Matcher matcherCase = Patterns.Case.matcher(content);
		if (matcherCase.find()) {
			if (!content.matches("^\\s*case[\\W\\w]+")) {
				this.addError(enums.Error.IncorrectExpressionInSwitch);
			}
			do {
				String caseSpace = "case;";
				for (int i = 0; i < matcherCase.group().length(); i++) {
					if (matcherCase.group().charAt(i) == '\n') {
						caseSpace = caseSpace+ "\ncase;";
					}
				}
				content = content.replaceAll(matcherCase.group(), caseSpace);
				matcherCase = Patterns.Case.matcher(content);
			} while (matcherCase.find());
		} else {
			this.addError(enums.Error.NoCaseInSwitch);
		}
		return;
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() 
	{
		return branch + "Switch";
	}

	@Override
	public boolean isValid() {
		try {
			if (condition != null)
				condition.isValid();
		} catch (InvalidOperator | InvalidFunction e) {
			this.addError(e.getError());
			return false;
		}
		return true;
	}

}
