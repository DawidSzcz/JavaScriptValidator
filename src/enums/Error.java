package enums;

public enum Error {
	InvalidBlock("InvalidBlock","Nieprawidlowy blok"),
	InvalidArguments("InvalidArguments","Nieprawidlowy argument"),
	UnexpectedOpeningBracket("Unexpected opening bracket","Nieoczekiwany nawias otwierajacy"),
	UnexpectedClosingBracket("Unexpected closing bracket","Nieoczekiwany nawias zamykajacy"),	
	UnparsedLine("Unparsed line","Nieprzetworzona linia"),
	MissingOpenningBracket("Missing opening bracket","Nie ma otwierajacego nawiasu"),
	WrongNumberOfArguments("Wrong number of arguments","Nie prawidlowy numer w argumencie"),
	WrongAssignment("Wrong assignment","Nieprawidlowa operacja"),
	MissingIfBeforeElse("MissingIfBeforeElse","Brakuje \"if\" przed \"else\""),
	InvalidOperator("Invalid operator","Nieprawidlowy operator"),
	InvalidFunction("Invalid function","Nieprawidlowa funkcja"),
	InvalExpresionInSquareBracket("Inval expresion in square bracket","Nieprawidlowe wyrazenie w nawiasie kwadratowym"),
	InvalExpresionInParenthesis("Inval Expresion In Parenthesis","Nieprawidlowe wyrazenie w nawiasie okraglym"),
	InvalidElseName("Invalid Else Name","Nieprawidlowa nazwa else-a"),
	InvalidElseIf("Invalid If in ElseIf","Nielrawid³owy \"if\" w \"else if\""),
	InvalidForCondition("Invalid For Condition"," Nieprawidlowy \"for\""),
	InvalidBeginning("Invalid beggining of complex statement","Nieprawidlowy pocz¹tek"),
	ForbidenCharacterInHeader("Forbiden character in header","Brak litery w naglowku"),
	InvalidCondition("Invalid Condition","Nielrawd³owy warunek"),
	MissingTryBeforeCatch("Missing try before catch","Nie znaleŸono \"try\" przed \"catch\""),
	InvalidString("String is invalid","Nieprawidlowy String"),
	EnterInString("String is not properly terminated","String jest niezakoñczony"),
	InvalidParenthesis("Invalid parenthasis","Niepoprawny nawias"),
	InvalidEscape("Invalid escape sequence in string","Nieprawidlowe wjscie ze Stringa"),
	RestrictedLowerCase("Lower case is mandatory in restricted Words", "S³owa kluczowe powinny zawieraæ wy³¹cznie ma³e litery"),
	SqlPortIsNotOpen("Sql port isn't open","port SQL-a nie zostal otwarty"),
	NullSteatment("Null Steatment","puste wyrazenie"),
	NullInBracket("No expresion in bracket","niema wyrazenia w nawiasach"),
	MissingEndOfComment("Missing end of comment","Brakuje konca komentarza"),
	IncorectExpresionWithUnderscore("Incorect expresion with underscore","Niepoprawne wyra¿enie z podkreslnikiem"),
	FunctionIsNotDeclared("Function is not declared", "Funkcja nie zosta³a zadeklarowana"),
	IncorrectNumberOfArguments("Incorrect number of arguments","Niepoprawna ilosc argumentow"),
	SomethingWrongWithElseIf("Somfing wrong with elseif", "Cos sie nie zgadza w instrukcji else if"),
	TryWithNoCatch("Missing catch block after try", "Brakuj¹cy catch po try"),
	MisssDotBeforFunctions("Misss dot befor functions","brakuje kropki przed funkcj¹"),
	InvalidUseGetWFLIProcessId("Invalid use getWFLIProcessId","nieprawidlowe uzycie getWFLIProcessId");

	public final String enContent;
	public final String plContent;
	Error(String en, String pl)
	{
		enContent = en;
		plContent = pl;
	}
//	public String getEn() {
//        return enContent;
//    }
//
//    public String getPl() {
//        return plContent;
//    }
//	public String toString()
//	{
//		return enContent;
//	}
	
}
