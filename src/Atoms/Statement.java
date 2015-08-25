package Atoms;

import java.util.regex.Matcher;

import enums.Error;
import exception.InvalidExpression;
import exception.InvalidString;
import simpleExpression.ExpresionCorrect;
import simpleExpression.Patterns;
import validator.Context;

public class Statement {
	private String name;

	public Statement(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		return;
	}

	public boolean isValid() throws InvalidExpression, InvalidString{
		Matcher stringId = parser.Patterns.stringID.matcher(name);
		while (stringId.find()) {
				if (!Context.strings.get(stringId.group()).getErrors().isEmpty()) {
					for(Error error:Context.strings.get(stringId.group()).getErrors())
						throw new InvalidString(error,name,Context.strings.get(stringId.group()).getLine());
				}
		}

		return true;//ExpresionCorrect.isExpressinCorrect(name);

	}
}
