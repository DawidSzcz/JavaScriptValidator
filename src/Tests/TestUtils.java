package Tests;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import simpleExpression.ExpresionCorrect;

public class TestUtils {
	public static String  readFromFile(String path) throws IOException
	{
			String line, input = "";
			FileReader file = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(file);
			while ((line = bufferedReader.readLine()) != null) {
				input += line + "\n";
			}
			bufferedReader.close();
			return input;
	}
	public static boolean testStatement(String str)
	{
		try{
			ExpresionCorrect.isExpressinCorrect(str);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
}
