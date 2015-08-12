package expression;

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
	public Switch(String statement, int currentLine, Map<String, StringContainer> strings) {
		super(statement, Instruction.SWITCH, currentLine, strings);
		if (content != null) {
			this.statements = Context.expressionParser.parseExpressions(content, beginOfStatements);
		}
	}

	public void splitBlock(Instruction instruction, int currentLine, String in) throws WrongComplexException {
		super.splitBlock(instruction, currentLine, in);
		Matcher matcherCase = Patterns.Case.matcher(content);
		if (matcherCase.find()) {
			if (content.indexOf("//s*"+Patterns.CaseS) != 0) {
				// error
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
			// error
		}
		return;
	}

	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Switch";
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
