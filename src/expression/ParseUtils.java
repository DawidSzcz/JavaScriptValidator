package expression;

import java.util.List;
import java.util.regex.Matcher;

public class ParseUtils {

	public static String cleanLine(String statement) {
		Matcher matcher = Patterns.line.matcher(statement);
		if(!matcher.find())
			throw new IllegalStateException();
		return matcher.group();
	}

	public static int getLine(List<String> instructions, String group) {
		group = cleanLine(group);
		for(int i = 0; i < instructions.size(); i++)
			try{
				if(group.contains(cleanLine(instructions.get(i))))
					return i + 1;
			}catch(IllegalStateException e){}
		return -2;
	}

}

