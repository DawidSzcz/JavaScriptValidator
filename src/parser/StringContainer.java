package parser;

import enums.Error;

public class StringContainer  {
	public Error error;
	public String string;
	public StringContainer(String string) {
	error = null;
	this.string = string;
}
/*	
	public Error getError() {
		return error;
	}
	public void setError(Error set){
		error=set;
	}

	public String getString() {
		return string;
	}
	public void setString(String set){
		string=set;
	}
*/
}
