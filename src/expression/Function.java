package expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Atoms.Statement;
import enums.Error;
import enums.Instruction;
import exception.ExceptionContainer;
import exception.InvalidExpression;
import exception.InvalidString;
import parser.Patterns;
import validator.Context;

public class Function extends ComplexExpression {
	List<Statement> arguments = new LinkedList<Statement>();
	List<String> args;
	String functionName;

	public Function(String statement, int currentLine, List<String> labels, String branch) {
		super(statement, currentLine, labels);
		Matcher checkBeginning = Pattern.compile(String.format(Patterns.beginComplexS, Instruction.FUNCTION))
				.matcher(statement);
		if (!checkBeginning.find()) {
			this.addError(Error.RestrictedLowerCase, line);
		}
		this.branch = branch;
		args = makeArgs(statement);
		for (String arg : args)
			arguments.add(new Statement(arg));
		functionName = getName(statement);
		if (Context.functions.containsKey(functionName)) {
			Context.functions.get(functionName).add(args.size());
		} else {
			List<Integer> list = new ArrayList<Integer>();
			list.add(args.size());
			Context.functions.put(functionName, list);
		}
	}

	@Override
	public String toString() {
		return branch + "Function";
	}

	@Override
	public boolean isValid() {
		boolean valid = super.isValid();
		for (Statement condtioniterator : arguments) {
			try {
				condtioniterator.isValid();
			} catch (ExceptionContainer ex) {
				for(InvalidExpression e : ex.getInlineExceptions())
					addError(e.getError(), line + e.getLine());
				for(InvalidString e : ex.getBeginningExceptions())
					addError(e.getError(), e.getLine());
				valid =  false;
			}
		}
		return valid;
	}

	private String getName(String statement) {
		statement = statement.substring(0, statement.indexOf('('));
		String name = statement.replaceAll("function", "");
		name = parser.ParseUtils.cleanLine(name);
		return name;
	}

	public List<String> makeArgs(String in) {
		List<String> list = new LinkedList<>();
		String currentArg = "";
		int opened = 1;
		for (int i = in.indexOf('(') + 1; i < in.length(); i++) {
			if (opened == 1 && in.charAt(i) == ',') {
				list.add(currentArg);
				currentArg = "";
				continue;
			}
			if (in.charAt(i) == '(')
				opened++;
			if (in.charAt(i) == ')') {
				opened--;
				if (opened == 0) {
					list.add(currentArg);
					break;
				}
			}
			currentArg += in.charAt(i);
		}
		return list;
	}
}
