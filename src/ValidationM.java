import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationM {
	
	public static String comentaryVariable (ArrayList<String> javaScriptText){
		String newTekst= new String();
		Boolean noError=true;
		Boolean addTekst=true;
		int licznik=0;
		for (int iterator2=0; iterator2<javaScriptText.size(); iterator2++){
			String row = javaScriptText.get(iterator2);
			for (int iterator=0; iterator<row.length(); iterator++ ){	
				
				if (iterator != row.length()-1 && row.charAt(iterator)=='/' && row.charAt(iterator+1)=='*' ){
					licznik++;
					addTekst=false;
				}
					
				if (addTekst)
					newTekst+=javaScriptText.get(iterator2).charAt(iterator);
				
				if (iterator != 0 && row.charAt(iterator-1)=='*' && row.charAt(iterator)=='/' ){
					licznik--;
					addTekst=true;
				}
				
					
				if (licznik<0){
					noError= false;
				}
			}
		}
	
		if (licznik!=0){
			noError= false;
		}
		if (!noError)
			newTekst+=" ERROR";
		
		return newTekst;
	}
	public static ArrayList<String> group (String javaScriptText){
		ArrayList<String> functionList= new ArrayList<String>();
		String function ="";
		int iloscpetli=0;
		Pattern pattern = Pattern.compile("[^\\n]+[\\s]+\\{[^\\}\\{]+\\}");
		Matcher matcher = pattern.matcher(javaScriptText);
		while(matcher.find()){
			function=matcher.group();
			functionList.add(function);
			javaScriptText.replace(function, Integer.toString(iloscpetli));
			++iloscpetli;
		}
			

		functionList.add(javaScriptText);
		
		return functionList;
	}
	
}
