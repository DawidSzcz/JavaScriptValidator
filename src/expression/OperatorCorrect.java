package expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperatorCorrect {

	public static String isOpreratorCorrect(String expression){
		int indeks = findOperator(expression);
		if (indeks!=0){
			String subExpression1=expression.substring(0,indeks);
			String subExpression2=expression.substring(indeks+1);
			return isOpreratorCorrect(subExpression1)+isOpreratorCorrect(subExpression2);
		}
		else{
			if(isExpressionCorrect(expression)){
				return "";	
			}
			else{
				return expression+" is incorrect";
			}
		}
	}

	private static boolean isExpressionCorrect(String expression) {
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(expression);
		if (matcher.find()){
			if (matcher.group().compareTo(expression)==0){
				return true;
			}
		}
		return false;
	}

	private static int findOperator(String expression) {
		for(int i=0; i<expression.length(); i++){
			if (expression.charAt(i)=='+'||expression.charAt(i)=='-'||expression.charAt(i)=='*'||expression.charAt(i)=='/'){
				return i;
			}
		}
		
		return 0;
	}
}
