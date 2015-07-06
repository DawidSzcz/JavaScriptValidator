package expression;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javafx.util.Pair;

public class ExpressionIterator 
{
	Stack<Pair<Expression, Integer>> stack = new Stack<>();
	public ExpressionIterator(List<Expression> exps) {
		stack.push(new Pair<Expression, Integer>(new Top(exps), 0));
	}
	public String next()
	{
		if(stack.isEmpty())
			return null;
		Pair<Expression, Integer> pair = stack.pop();
		try{
			Expression exp = pair.getKey().get(pair.getValue());
			stack.push(new Pair<Expression, Integer>(pair.getKey(), pair.getValue() + 1));
			stack.push(new Pair<Expression, Integer>(exp, 1));
			return makeTree();
		}catch(IndexOutOfBoundsException ex){
			return next();
		}
	}
	private String makeTree() {
		String ret = "";
		for(int i = 0; i <stack.size(); i++)
			ret += stack.get(i).getKey().toString() + " ";
		return ret;
	}
	private class Top extends Expression
	{
		List<Expression> topExpressions;
		public Top(List<Expression> exps) {
			topExpressions = exps;
		}
		@Override
		public Expression get(int index) throws IndexOutOfBoundsException {
			return topExpressions.get(index);
		}
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "";
		}
	}
}
