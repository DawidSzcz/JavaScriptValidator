package Atoms;

import java.util.regex.Matcher;

import enums.Error;
import exception.ExceptionContainer;
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

	public boolean isValid() throws ExceptionContainer{
		Matcher stringId = parser.Patterns.stringID.matcher(name);
		ExceptionContainer exception = new ExceptionContainer();
		
		try{
			ExpresionCorrect.isExpressinCorrect(name);
		}catch(ExceptionContainer e)
		{
			exception = e;
		}
		while (stringId.find()) {
				if (!Context.strings.get(stringId.group()).getErrors().isEmpty()) {
					for(Error error:Context.strings.get(stringId.group()).getErrors())
						exception.addException(new InvalidString(error,name,Context.strings.get(stringId.group()).getLine()));
				}
		}
		if(!exception.isEmpty())
			throw exception;
		return true;

	}
}
