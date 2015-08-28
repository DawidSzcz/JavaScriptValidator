package enums;

public enum Error {
	InvalidBlock("InvalidBlock","Nieprawid³owy blok"),
	InvalidArguments("InvalidArguments","Nieprawid³owy argument"),
	UnexpectedOpeningBracket("Unexpected opening bracket","Nieoczekiwany nawias otwierajacy"),
	UnexpectedClosingBracket("Unexpected closing bracket","Nieoczekiwany nawias zamykajacy"),	
	UnparsedLine("Unparsed line","Nieprzetworzona linia"),
	MissingOpenningBracket("Missing opening bracket","Nie ma otwierajacego nawiasu"),
	WrongNumberOfArguments("Wrong number of arguments","Nieprawid³owy numer w argumencie"),
	WrongAssignment("Wrong assignment","Nieprawidlowa operacja"),
	MissingIfBeforeElse("MissingIfBeforeElse","Brakuje \"if\" przed \"else\""),
	SyntaxError("Syntax error","b³¹d sk³adni"),
	InvalExpresionInSquareBracket("Inval expresion in square bracket","Nieprawid³owe wyra¿enie w nawiasie kwadratowym"),
	InvalExpresionInParenthesis("Inval Expresion In Parenthesis","Nieprawid³owe wyrazenie w nawiasie okrag³ym"),
	InvalidElseName("Invalid Else Name","Nieprawid³owa nazwa else-a"),
	InvalidElseIf("Invalid If in ElseIf","Nielrawid³owy \"if\" w \"else if\""),
	InvalidForCondition("Invalid For Condition"," Nieprawid³owy \"for\""),
	InvalidBeginning("Invalid beggining of complex statement","Nieprawid³owy pocz¹tek"),
	ForbidenCharacterInHeader("Forbiden character in header","Brak litery w nag³ówku"),
	InvalidCondition("Invalid Condition","Nielrawd³owy warunek"),
	MissingTryBeforeCatch("Missing try before catch","Nie znaleŸono \"try\" przed \"catch\""),
	InvalidString("String is invalid","Nieprawid³owy String"),
	EnterInString("String is not properly terminated","String jest niezakoñczony"),
	InvalidParenthesis("Invalid parenthasis","Niepoprawny nawias"),
	InvalidEscape("Invalid escape sequence in string","Nieprawid³owe wyjœcie ze Stringa"),
	RestrictedLowerCase("Lower case is mandatory in restricted Words", "S³owa kluczowe powinny zawieraæ wy³¹cznie ma³e litery"),
	SqlPortIsNotOpen("Sql port isn't open","port SQL-a nie zosta³ otwarty"),
	NullSteatment("Null Steatment","puste wyra¿enie"),
	NullInBracket("No expresion in bracket","niema wyra¿enia w nawiasach"),
	MissingEndOfComment("Missing end of comment","Brakuje koñca komentarza"),
	IncorectExpresionWithUnderscore("Incorect expresion with underscore","Niepoprawne wyra¿enie z podkreslnikiem"),
	FunctionIsNotDeclared("Function is not declared", "Funkcja nie zosta³a zadeklarowana"),
	SomethingWrongWithElseIf("Somfing wrong with elseif", "Coœ siê nie zgadza w instrukcji else if"),
	TryWithNoCatch("Missing catch block after try", "Brakuj¹cy catch po try"),
	MisssDotBeforFunctions("Misss dot befor functions","brakuje kropki przed funkcj¹"),
	InvalidUseGetWFLIProcessId("Invalid use getWFLIProcessId","nieprawid³owe u¿ycie getWFLIProcessId"),
	NoCaseInSwitch("No case in switch", "switch nie posiada caseow"),
	IncorrectExpressionInSwitch("Incorrect expression in switch","niepoprwane wyra¿enie w switchu"),
	MissingLabelDeclaration("Label declaration is missing", "brakuje definicji etykiety"), 
	ControlStatementNotInLoop("Control statement should be in loop", "Wyra¿enie steruj¹ce poza pêtl¹"),
	UsedKeyWord("Used key word!","u¿yto s³owo kluczowe!"),
	ExpectedVariableNotNumber("Expected variable not number","Lczba zamiast zmiennej"),
	IncorrectDeclaredException("Incorrect declared exception","Niepoprawne zadeklarowany wyj¹tek"),
	InvalidToken("Invalid token","brak znaku"),
	IncorrectMark("Incorrect mark","nie poprawny znak"),
	TripleOperator("Triple operator","potrójny operator"),
	InvalidFunction("Invalid expression in funktion","Nieprawid³owe wyra¿enie w funkcji");

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
