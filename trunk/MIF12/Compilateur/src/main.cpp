/* --- Bibliothèques --- */
#include <stdio.h>
//#include <stdlib.h>
#include <iostream>

/* --- Headers projet --- */
#include "TableDesIdentificateurs.hpp"
//#include "TableDesSymboles.hpp"


using namespace std;


/* --- Variables --- */
extern FILE* yyin;
extern int yyleng;
extern char* yytext;
extern int yylex();
extern int yyparse();


TableDesIdentificateurs* tableId = NULL;
//TableDesSymboles* tableSymb;


int main(int argc, char** argv)
{
	yyin = NULL;
	if(argc > 1) yyin = fopen( argv[1], "r" ); else yyin = stdin;

	tableId = new TableDesIdentificateurs();
	//tableSymb = new TableDesSymboles();

	yylex();
	yyparse();

	tableId->afficherTable();

	if(yyin != NULL)
	{
	    fclose(yyin);
	}

	delete tableId;

	return 0;
}
