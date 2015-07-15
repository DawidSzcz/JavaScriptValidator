package Tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import operator.OperatorCorrect;

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
			OperatorCorrect.isOpreratorCorrect(str);
			return true;
		}catch(Exception e)
		{
			return false;
		}
	}
}
