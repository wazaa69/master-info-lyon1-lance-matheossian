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
TableDesIdentificateurs* tableId = new TableDesIdentificateurs();
TableDesSymboles* tableSymb = new TableDesSymboles();
std::vector<TableDesSymboles*> listeTDS;


int main(int argc, char** argv)
{
    yyin = NULL;

    if(argc > 1) yyin = fopen( argv[1], "r" );
    else yyin = stdin;


    //yylex();
    yyparse();

    //affichage les tables
    std::cout << "\nTable des identifiants: \n"<<std::endl;
    tableId->afficherTable();

    //std::cout << "\nTable des symboles: \n"<<std::endl;
   // tableSymb->afficherTable();
    
     std::cout << std::endl;
     tableSymb->afficherTables(listeTDS);
	
    if(yyin != NULL)
        fclose(yyin);


    //supression des instances
    delete tableId;
    delete tableSymb;

    return 0;
}
