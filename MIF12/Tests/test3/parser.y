%{
    #include <cstdio>
    #include <iostream>
    #include <vector>
    #include <string>

    using namespace std;

    #include "TDS.hpp"
    #include "Symbole.hpp"

    #include "Type.hpp"
    #include "TypeInteger.hpp"
    #include "TypeReal.hpp"
    #include "TypeBoolean.hpp"
    #include "TypeChar.hpp"
    #include "TypeString.hpp"
    #include "TypePointeur.hpp"
    #include "TypeInterval.hpp"
    #include "TypeArray.hpp"
    #include "TypeRecord.hpp"

    extern FILE* yyin;
    extern char* yytext;
    extern int yylex();
    extern int yyerror(char* m);

    extern TableDesSymboles* tableSymb; //la table principale des symboles
    extern std::vector<TableDesSymboles*> listeTDS; // pour pouvoir stocker toutes les tables de symboles des différents contextes

    TableDesSymboles* tmpTds =  new TableDesSymboles(tableSymb->incNumContexteActuel()); //une table des symboles temporaires (pour les sous-contextes)
    std::vector<int> tmpNumId; //pour connaître le nombre d'identifiant d'un même type (utilisé pour remplir la TDS)
  
    bool nouveauRecord = true; // booleen indiquant le début de la déclaration d'un record pour pouvoir décaler d'un cran tmpTds
    int diffRecord; // 
    int numIdRecord; // numero Id du record à écrire dans la table des symboles
    bool ajoutRecord = false; // booleen servant à indiquer si le dernier type remonté est un record pour pouvoir attribuer le bon id dans la table des symboles

%}

%token KW_PROGRAM
%token KW_VAR
%token KW_BEGIN
%token KW_END
%token KW_INTEGER
%token KW_REAL
%token KW_BOOLEAN
%token KW_CHAR
%token KW_STRING
%token KW_ARRAY
%token KW_OF
%token KW_TYPE
%token KW_RECORD

%token SEP_SCOL
%token SEP_DOT
%token SEP_DOTS
%token SEP_COMMA
%token SEP_CO
%token SEP_CF
%token SEP_DOTDOT

%token OP_PTR
%token OP_SUB
%token OP_EQ

%token <numero> TOK_IDENT
%token TOK_INTEGER

%start Program


%type <type> Type
%type <type> BaseType
%type <typeInterval> InterType
%type <interBase> InterBase
%type <typeArray> ArrayType
%type <typePointeur> PointerType
%type <typeRecord> RecordType

/* Les types */

%union{

    int numero;
    Type* type;
    TypeInterval* typeInterval;
    int interBase;
    TypeArray* typeArray;
    TypePointeur* typePointeur;
    TypeRecord* typeRecord;


}


%%

Program         : ProgramHeader SEP_SCOL Block SEP_DOT          {}
                ;

ProgramHeader   : KW_PROGRAM TOK_IDENT                          {	
									listeTDS.push_back(tableSymb);
									tableSymb->ajouter(new Symbole("prog"));
								}
                ;


Block         :  BlockDeclType BlockDeclVar BlockCode		{}
              ;


BlockDeclType  : KW_TYPE ListDeclType				
               |						
               ;

ListDeclType   : ListDeclType DeclType				
               | DeclType					
               ;

DeclType       : TOK_IDENT OP_EQ Type SEP_SCOL			





BlockDeclVar    : KW_VAR ListDeclVar                            {}
                |                                               {}
                ;

ListDeclVar     : ListDeclVar DeclVar                           {}
                | DeclVar                                       {}
                ;

DeclVar         : ListIdent SEP_DOTS Type SEP_SCOL
                                                                {

                                                                    for(unsigned int i = 0; i < tmpNumId.size() ; i++){
									
									if (!ajoutRecord){
									tableSymb->ajouter(new Symbole("variable", $3, tableSymb->incNumIdActuel())); 
									}
                                                                        else{tableSymb->ajouter(new Symbole("variable", $3, numIdRecord)); ajoutRecord = false;}
															
                                                                        cout << *($3->getStringType()) <<  " ajoute à TS principale" <<  endl;
                                                                    }

                                                                    tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante						     
									cout << "On efface tmpNumId" << endl;

                                                                }
                ;

ListIdent        :    ListIdent SEP_COMMA TOK_IDENT             {tmpNumId.push_back($3);}
                 |    TOK_IDENT                                 {tmpNumId.push_back($1);}
                 ;



Type            :    TOK_IDENT							{}	                
		|    UserType							{}
		|    BaseType							{}
                ;

BaseType	:    KW_INTEGER 				                {$$ = new TypeInteger();}
                |    KW_REAL 					                {$$ = new TypeReal();}
                |    KW_BOOLEAN 				                {$$ = new TypeBoolean();}
                |    KW_CHAR 					                {$$ = new TypeChar();}
                |    KW_STRING 							{$$ = new TypeString();}
               

UserType       :    ArrayType							{}
	       |    InterType							{}
	       |    PointerType							{}
	       |    RecordType							{}
	       ;		

RecordType     : KW_RECORD RecordFields KW_END					{
								
								
								TypeRecord* tmpRec = new TypeRecord(tmpTds);
								
								tmpNumId.push_back(0);
							        $$ = tmpRec; // on remonte le record
								
								cout <<  "TS" << tmpTds->getNumContexte() << " a une taile de  " << tmpTds->getTableSymb().size() << " et est envoyé dans la liste des TS \n\n"<< endl;

								listeTDS.push_back(tmpTds); // on rajoute le nouveau contexte dans la liste des TS
								
										}
               ;

RecordFields   : RecordFields SEP_SCOL RecordField				{}				
               | RecordField							{}
               ;



RecordField    : ListIdent SEP_DOTS Type					{

		// on doit enlever 1 à la taille de tmpTds car il compte le TOKIDENT du record pendant la déclaration
		if(nouveauRecord){
			diffRecord = 1;
			nouveauRecord = false; 
			numIdRecord = tableSymb->incNumIdActuel();
			ajoutRecord = true;

			tmpTds = new TableDesSymboles(tableSymb->incNumContexteActuel()); // on initialise tmpTds pour le nouveau contexte
			cout <<  "\n\nCréation TS" << tmpTds->getNumContexte() << endl;
		}

		else{diffRecord = 0;}
				
			
		for(unsigned int i = 0; i < tmpNumId.size() - diffRecord ; i++){
				
				
                               tmpTds->ajouter(new Symbole("variable", $3, tableSymb->incNumIdActuel()));
				
                               cout << *($3->getStringType()) <<  " a été ajouté à la table des symboles temporaire." << tableSymb->getNumIdActuel() <<endl;

                   }

                tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante
}







ArrayType      : KW_ARRAY SEP_CO ArrayIndex SEP_CF KW_OF Type    { /*   array[ 6 .. 10 ] of integer  ou array[ a .. b ] of real */ 
								   $$ = new TypeArray();
               ;						}

ArrayIndex     : TOK_IDENT					{/*  a ou  6 .. 10 */}
               | InterType					{/* on pourrait ramener un intervalle pour les 2 cas à rajouter dans arraytype */}


InterType      : InterBase SEP_DOTDOT InterBase             {/* 6 .. 10  ou a .. 15 ou -7 .. b   */
								
								 $$ = new TypeInterval($1,$3);
							     }
               ;

PointerType    : OP_PTR Type			{$$ = new TypePointeur(*$2);}
               ;



InterBase      : NSInterBase			{ /* a ou 6 */}
               | OP_SUB NSInterBase		{ /* - a ou -6 */}
               ;

NSInterBase    : TOK_IDENT			{ /* a */}
               | TOK_INTEGER			{ /* integer */ }
               ;


BlockCode       : KW_BEGIN ListInstr KW_END                     {}
                ;

ListInstr       :
                ;





%%
