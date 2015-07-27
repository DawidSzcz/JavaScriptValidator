package Atoms;

import java.util.List;

import enums.Error;

public class StringContainer  {
	private List<Error> errors;
	private String content;
	public StringContainer(String string) 
	{
		this.content = string;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public void addError(Error error){
		errors.add(error);
	}

	public String getString() {
		return content;
	}
	public void addErrors(List<Error> errors2) {
		errors.addAll(errors);
		
	}
}
