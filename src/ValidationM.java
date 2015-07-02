import java.util.ArrayList;

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
	public static String cos (ArrayList<String> javaScriptText){
		String error= new String();
		return error;
	}
}
