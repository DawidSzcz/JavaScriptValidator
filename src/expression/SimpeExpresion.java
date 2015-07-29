package expression;

import java.util.Map;
import java.util.regex.Matcher;

import Atoms.StringContainer;
import exception.InvalidOperator;
import parser.Patterns;

public class SimpeExpresion extends Expression {
	
	static boolean isPortOpen=false;
	
	public SimpeExpresion(String name, int currentLine, Map<String, StringContainer> strings) {
		super(name, currentLine, strings);
	}

	private void isPortOpen(String text){
		Matcher matcherPortFunktion = Patterns.sqlGetPortFunction.matcher(text);
		if (matcherPortFunktion.find()){
			isPortOpen=true;
		}
	}
//	public void 
	@Override
	public Expression get(int index) throws IndexOutOfBoundsException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isValid() {
		isPortOpen(name);
		Matcher matcherExecuteStetmentFunction= Patterns.sqlExecuteStetmentFunction.matcher(name);
		if(matcherExecuteStetmentFunction.find()){
			if(isPortOpen){
				return true;
			}else{
				this.addError(enums.Error.SqlPortIsNotOpen);
				return false;
			}
		}
		return true;
	}
}
