package enums;

public enum Error {
	InvalidBlock("InvalidBlock","Nieprawidlowy blok"),
	InvalidArguments("InvalidArguments","Nieprawid這wy argument"),
	UnexpectedOpeningBracket("Unexpected opening bracket","Nieoczekiwany nawias otwierajacy"),
	UnexpectedClosingBracket("Unexpected closing bracket","Nieoczekiwany nawias zamykajacy"),	
	UnparsedLine("Unparsed line","Nieprzetworzona linia"),
	MissingOpenningBracket("Missing opening bracket","Nie ma otwierajacego nawiasu"),
	WrongNumberOfArguments("Wrong number of arguments","Nieprawid這wy numer w argumencie"),
	WrongAssignment("Wrong assignment","Nieprawidlowa operacja"),
	MissingIfBeforeElse("MissingIfBeforeElse","Brakuje \"if\" przed \"else\""),
	SyntaxError("Syntax error","b章d sk豉dni"),
	InvalExpresionInSquareBracket("Inval expresion in square bracket","Nieprawid這we wyra瞠nie w nawiasie kwadratowym"),
	InvalExpresionInParenthesis("Inval Expresion In Parenthesis","Nieprawid這we wyrazenie w nawiasie okrag造m"),
	InvalidElseName("Invalid Else Name","Nieprawid這wa nazwa else-a"),
	InvalidElseIf("Invalid If in ElseIf","Nielrawid這wy \"if\" w \"else if\""),
	InvalidForCondition("Invalid For Condition"," Nieprawid這wy \"for\""),
	InvalidBeginning("Invalid beggining of complex statement","Nieprawid這wy pocz靖ek"),
	ForbidenCharacterInHeader("Forbiden character in header","Brak litery w nag堯wku"),
	InvalidCondition("Invalid Condition","Nielrawd這wy warunek"),
	MissingTryBeforeCatch("Missing try before catch","Nie znale�ono \"try\" przed \"catch\""),
	InvalidString("String is invalid","Nieprawid這wy String"),
	EnterInString("String is not properly terminated","String jest niezako鎍zony"),
	InvalidParenthesis("Invalid parenthasis","Niepoprawny nawias"),
	InvalidEscape("Invalid escape sequence in string","Nieprawid這we wyj�cie ze Stringa"),
	RestrictedLowerCase("Lower case is mandatory in restricted Words", "S這wa kluczowe powinny zawiera� wy章cznie ma貫 litery"),
	SqlPortIsNotOpen("Sql port isn't open","port SQL-a nie zosta� otwarty"),
	NullSteatment("Null Steatment","puste wyra瞠nie"),
	NullInBracket("No expresion in bracket","niema wyra瞠nia w nawiasach"),
	MissingEndOfComment("Missing end of comment","Brakuje ko鎍a komentarza"),
	IncorectExpresionWithUnderscore("Incorect expresion with underscore","Niepoprawne wyra瞠nie z podkreslnikiem"),
	FunctionIsNotDeclared("Function is not declared", "Funkcja nie zosta豉 zadeklarowana"),
	SomethingWrongWithElseIf("Somfing wrong with elseif", "Co� si� nie zgadza w instrukcji else if"),
	TryWithNoCatch("Missing catch block after try", "Brakuj鉍y catch po try"),
	MisssDotBeforFunctions("Misss dot befor functions","brakuje kropki przed funkcj�"),
	InvalidUseGetWFLIProcessId("Invalid use getWFLIProcessId","nieprawid這we u篡cie getWFLIProcessId"),
	NoCaseInSwitch("No case in switch", "switch nie posiada caseow"),
	IncorrectExpressionInSwitch("Incorrect expression in switch","niepoprwane wyra瞠nie w switchu"),
	MissingLabelDeclaration("Label declaration is missing", "brakuje definicji etykiety"), 
	ControlStatementNotInLoop("Control statement should be in loop", "Wyra瞠nie steruj鉍e poza p皻l�"),
	UsedKeyWord("Used key word!","u篡to s這wo kluczowe!"),
	ExpectedVariableNotNumber("Expected variable not number","Lczba zamiast zmiennej"),
	IncorrectDeclaredException("Incorrect declared exception","Niepoprawne zadeklarowany wyj靖ek"),
	InvalidToken("Invalid token","brak znaku"),
	IncorrectMark("Incorrect mark","nie poprawny znak"),
	TripleOperator("Triple operator","potr鎩ny operator"),
	InvalidFunction("Invalid expression in funktion","Nieprawid這we wyra瞠nie w funkcji");

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
