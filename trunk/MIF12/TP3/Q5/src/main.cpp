#include <stdio.h>
#include <iostream>

#include "TDI.hpp"
#include "TDS.hpp"
#include "TypeUser.hpp"
#include "Argument.hpp"
#include "Temporaire.hpp"
#include "ConteneurCode.hpp"
#include "Factory.hpp"


extern FILE* yyin;
extern int yyleng;
extern char* yytext;
extern int yylex();
extern int yyparse();


/* Déclaration gloable pour pouvoir les utiliser dans flex ou bison */

	TableDesIdentificateurs* tableId = new TableDesIdentificateurs(); // pour récupérer les TOK_IDENT du lexer
	TableDesIdentificateurs* tableInteger = new TableDesIdentificateurs(); // pour récupérer les TOK_INTEGER du lexer
	TableDesIdentificateurs* tableReal = new TableDesIdentificateurs(); // pour récupérer les TOK_REAL du lexer
	TableDesIdentificateurs* tableString = new TableDesIdentificateurs(); // pour récupérer les TOK_STRING du lexer
	TableDesIdentificateurs* tablePtr = new TableDesIdentificateurs(); // pour récupérer les TOK_PTR du lexer
	TableDesIdentificateurs* tableBoolean = new TableDesIdentificateurs(); // pour récupérer les TOK_BOOLEAN du lexer

	TableDesSymboles* tmpTds = new TableDesSymboles(0); //une table des symboles temporaires (pour les différents contextes)
	TableDesSymboles* tableSymb = new TableDesSymboles();
	TypeUser* typeUser;

	std::vector<TableDesSymboles*> listeTDS;
	std::vector<int> tmpNumId;
	std::vector<Type*> tmpType;
	std::vector<TypeUser*> listeTypeUser;

//############################################### FONCTIONS/PROCEDURES

	std::vector<int> tabTDSPere;

//############################################### ARGUMENTS

	std::vector<Argument*> tabArguments;

//############################################### RECORDS

	TableDesSymboles* tmpRecord = new TableDesSymboles(0);

//############################################### USER TYPE

	TypeUser* symbTypeUserRemonte;

//############################################### TEMPORAIRES

	std::vector<Temporaire*> tabTemporaires;

//############################################### FACTORY

	Factory* usine;

//############################################### CODE 3@

	ConteneurCode* CCode = new ConteneurCode();

//############################################### ERREURS

	bool erreur = false;

int main(int argc, char** argv)
{   
    
  
    yyin = NULL;

    if(argc > 1) yyin = fopen( argv[1], "r" );
    else yyin = stdin;


    //yylex();
    yyparse();

    //affichage les tables

    if(!erreur){

    std::cout << "\nTable des identifiants: \n"<<std::endl;
    tableId->afficherTable();

     std::cout << std::endl;
     tableSymb->afficherTables(listeTDS);

	CCode->affichageCode3AD();
   }

    if(yyin != NULL)
        fclose(yyin);

    //supression des instances

	/*delete tableSymb;
	delete tableId;
	delete tableInteger;
	delete tableReal;
	delete tableString;
	delete tablePtr;
	delete tableBoolean;
*/

	listeTDS.clear();
	tmpNumId.clear();
	tmpType.clear();
	listeTypeUser.clear();
	tabTDSPere.clear();
	tabArguments.clear();
	tabTDSPere.clear();

 	


    return 0;
}
