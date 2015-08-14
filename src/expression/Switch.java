package expression;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import enums.Instruction;
import exception.InvalidExpression;
import exception.WrongComplexException;
import parser.Patterns;
import validator.Context;

public class Switch extends ComplexExpression {
	public Switch(String statement, int currentLine, List<String> labels, String branch) {
		super(statement, currentLine);
	}
//	@Override
//	public void splitBlock(Instruction instruction, int currentLine, String in, List<String> labels) throws WrongComplexException {
//		super.splitBlock(instruction, currentLine, in, labels);
//		Matcher matcherCase = Patterns.Case.matcher(content);
//		Matcher matcherDefault = Patterns.Default.matcher(content);
//		if (matcherCase.find()) {
//			if (!content.matches("^\\s*case[\\W\\w]+")) {
//				this.addError(enums.Error.IncorrectExpressionInSwitch);
//			}
//			do {
//				String caseSpace = "case;";
//				for (int i = 0; i < matcherCase.group().length(); i++) {
//					if (matcherCase.group().charAt(i) == '\n') {
//						caseSpace = caseSpace+ "\ncase;";
//					}
//				}
//				content = content.replaceAll(matcherCase.group(), caseSpace);
//				matcherCase = Patterns.Case.matcher(content);
//			} while (matcherCase.find());
//		} else {
//			this.addError(enums.Error.NoCaseInSwitch);
//		}
//		if (matcherDefault.find()){
//			content = content.replaceFirst(matcherDefault.group(),"default;");
//		}
//		return;
//	}

	@Override
	public String toString() 
	{
		return branch + "Switch";
	}

	@Override
	public boolean isValid() {
		super.isValid();
		try {
			if (condition != null)
				condition.isValid();
		} catch (InvalidExpression e) {
			this.addError(e.getError());
			return false;
		}
		return true;
	}

}