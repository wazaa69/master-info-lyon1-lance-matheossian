#include <stdio.h>
//#include <stdlib.h>
#include <iostream>

#include "TDI.hpp"
//#include "TableSymb.hpp"


using namespace std;

extern FILE* yyin;
extern int yyleng;
extern char* yytext;
extern int yylex();
extern int yyparse();
extern int nbID;

TableDesIdentificateurs* tableId;
//TableSymbole* tableSymb;

int main(int argc, char** argv)
{
    yyin = NULL;
        if(argc > 1)
                yyin = fopen( argv[1], "r" );
        else
                yyin = stdin;

        tableId = new TableDesIdentificateurs();
        //tableSymb = new TableSymbole();

        //yylex();
        yyparse();

        //afficher Tables
        tableId -> afficherTable(nbID);
    //cout << *tableSymb << endl;

        if(yyin != NULL)
        {
            fclose(yyin);
        }

      //  delete tableId;

        return 0;
}
