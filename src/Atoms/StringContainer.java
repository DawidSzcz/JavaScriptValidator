package Atoms;

import java.util.LinkedList;
import java.util.List;

import enums.Error;

public class StringContainer  {
	private List<Error> errors = new LinkedList<>();
	public String string;
	public StringContainer(String string) 
	{
		this.string = string;
	}
	public List<Error> getErrors() {
		return errors;
	}
	public void addError(Error error){
		errors.add(error);
	}

	public String getString() {
		return string;
	}
	public void addErrors(List<Error> errors2) {
		errors.addAll(errors2);
		
	}
}
