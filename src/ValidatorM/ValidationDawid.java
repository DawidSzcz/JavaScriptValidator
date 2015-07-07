package ValidatorM;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javafx.util.Pair;

public class ValidationDawid {
	public static List<List<String>> countBrackets(List<String> rows)
	{
		List<List<String>> messages = new LinkedList<List<String>>();
		int brackets = 0;
		int parenthesis = 0;
		Stack<Pair<Integer, Integer>> opendParanthases = new Stack<>();
		Stack<Pair<Integer, Integer>> opendBrackets = new Stack<>();
		int rowCounter = 0;
		for(String row : rows)
		{
			List<String> rowMessage = new LinkedList<>();
			for(int i = 0 ; i < row.length(); i++)
			{
				char c = row.charAt(i);
				if(c == '{')
				{
					brackets++;
					opendBrackets.push(new Pair<Integer, Integer>(rowCounter, i));
				}
				if(c == '}')
				{
					if(brackets > 0)
					opendBrackets.pop();
					brackets--;
				}
				if(c == '(')
				{
					opendParanthases.push(new Pair<Integer, Integer>(rowCounter, i));
					parenthesis++;
				}
				if(c ==')')
				{
					if(parenthesis > 0 )
						opendParanthases.pop();
					parenthesis--;
				}
				if(parenthesis < 0)
				{
					rowMessage.add("Error in column:" + i + "\n\t Closing parenthesis");
					parenthesis = 0;
				}
				if(brackets < 0)
				{
					rowMessage.add("Error in column:" + i + "\n\t Closing bracket");
					brackets = 0;
				}
			}	
			rowCounter++;
			messages.add(rowMessage);
		}

		for(Pair<Integer, Integer> p : opendBrackets)
		{
			messages.get(p.getKey()).add("Error in column:" + p.getValue() + "\n\t Opening parenthesis");
		}

		for(Pair<Integer, Integer> p : opendParanthases)
		{
			messages.get(p.getKey()).add("Error in column:" + p.getValue() + "\n\t Opening parenthesis");
		}
		return messages;
	}
}
