package enums;

public enum Error {
	InvalidBlock("InvalidBlock","Nieprawid�owy blok"),
	InvalidArguments("InvalidArguments","Nieprawid�owy argument"),
	UnexpectedOpeningBracket("Unexpected opening bracket","Nieoczekiwany nawias otwierajacy"),
	UnexpectedClosingBracket("Unexpected closing bracket","Nieoczekiwany nawias zamykajacy"),	
	UnparsedLine("Unparsed line","Nieprzetworzona linia"),
	MissingOpenningBracket("Missing opening bracket","Nie ma otwierajacego nawiasu"),
	WrongNumberOfArguments("Wrong number of arguments","Nieprawid�owy numer w argumencie"),
	WrongAssignment("Wrong assignment","Nieprawidlowa operacja"),
	MissingIfBeforeElse("MissingIfBeforeElse","Brakuje \"if\" przed \"else\""),
	SyntaxError("Syntax error","b��d sk�adni"),
	InvalExpresionInSquareBracket("Inval expresion in square bracket","Nieprawid�owe wyra�enie w nawiasie kwadratowym"),
	InvalExpresionInParenthesis("Inval Expresion In Parenthesis","Nieprawid�owe wyrazenie w nawiasie okrag�ym"),
	InvalidElseName("Invalid Else Name","Nieprawid�owa nazwa else-a"),
	InvalidElseIf("Invalid If in ElseIf","Nielrawid�owy \"if\" w \"else if\""),
	InvalidForCondition("Invalid For Condition"," Nieprawid�owy \"for\""),
	InvalidBeginning("Invalid beggining of complex statement","Nieprawid�owy pocz�tek"),
	ForbidenCharacterInHeader("Forbiden character in header","Brak litery w nag��wku"),
	InvalidCondition("Invalid Condition","Nielrawd�owy warunek"),
	MissingTryBeforeCatch("Missing try before catch","Nie znale�ono \"try\" przed \"catch\""),
	InvalidString("String is invalid","Nieprawid�owy String"),
	EnterInString("String is not properly terminated","String jest niezako�czony"),
	InvalidParenthesis("Invalid parenthasis","Niepoprawny nawias"),
	InvalidEscape("Invalid escape sequence in string","Nieprawid�owe wyj�cie ze Stringa"),
	RestrictedLowerCase("Lower case is mandatory in restricted Words", "S�owa kluczowe powinny zawiera� wy��cznie ma�e litery"),
	SqlPortIsNotOpen("Sql port isn't open","port SQL-a nie zosta� otwarty"),
	NullSteatment("Null Steatment","puste wyra�enie"),
	NullInBracket("No expresion in bracket","niema wyra�enia w nawiasach"),
	MissingEndOfComment("Missing end of comment","Brakuje ko�ca komentarza"),
	IncorectExpresionWithUnderscore("Incorect expresion with underscore","Niepoprawne wyra�enie z podkreslnikiem"),
	FunctionIsNotDeclared("Function is not declared", "Funkcja nie zosta�a zadeklarowana"),
	SomethingWrongWithElseIf("Somfing wrong with elseif", "Co� si� nie zgadza w instrukcji else if"),
	TryWithNoCatch("Missing catch block after try", "Brakuj�cy catch po try"),
	MisssDotBeforFunctions("Misss dot befor functions","brakuje kropki przed funkcj�"),
	InvalidUseGetWFLIProcessId("Invalid use getWFLIProcessId","nieprawid�owe u�ycie getWFLIProcessId"),
	NoCaseInSwitch("No case in switch", "switch nie posiada caseow"),
	IncorrectExpressionInSwitch("Incorrect expression in switch","niepoprwane wyra�enie w switchu"),
	MissingLabelDeclaration("Label declaration is missing", "brakuje definicji etykiety"), 
	ControlStatementNotInLoop("Control statement should be in loop", "Wyra�enie steruj�ce poza p�tl�"),
	UsedKeyWord("Used key word!","u�yto s�owo kluczowe!"),
	ExpectedVariableNotNumber("Expected variable not number","Lczba zamiast zmiennej"),
	IncorrectDeclaredException("Incorrect declared exception","Niepoprawne zadeklarowany wyj�tek"),
	InvalidToken("Invalid token","brak znaku"),
	IncorrectMark("Incorrect mark","nie poprawny znak"),
	TripleOperator("Triple operator","potr�jny operator"),
	InvalidFunction("Invalid expression in funktion","Nieprawid�owe wyra�enie w funkcji");

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
