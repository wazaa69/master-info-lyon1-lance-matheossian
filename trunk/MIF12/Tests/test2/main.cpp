#include <stdio.h>
#include <iostream>

#include "TDI.hpp"
#include "TDS.hpp"


extern FILE* yyin;
extern int yyleng;
extern char* yytext;
extern int yylex();
extern int yyparse();


/* Déclaration gloable pour pouvoir les utiliser dans flex ou bison */
TableDesIdentificateurs* tableId;
TableDesSymboles* tableSymb;


int main(int argc, char** argv)
{
    yyin = NULL;

    if(argc > 1) yyin = fopen( argv[1], "r" );
    else yyin = stdin;


    tableId = new TableDesIdentificateurs();
    tableSymb = new TableDesSymboles();


    //yylex();
    yyparse();

    //affichage les tables
    tableId->afficherTable();
    tableSymb->afficherTable();

    if(yyin != NULL)
        fclose(yyin);


    //supression des instances
    delete tableId;
    delete tableSymb;

    return 0;
}
