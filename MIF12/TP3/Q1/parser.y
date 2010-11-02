%{
	#include <cstdio>
	#include <iostream>
	#include <vector>
	#include <string>
	#include <sstream>

//############################################### TABLES

	#include "TDS.hpp"
	#include "TDI.hpp"

//############################################### SYMBOLES

	#include "Symbole.hpp"
	#include "Variable.hpp"
	#include "Fonction.hpp"
	#include "Procedure.hpp"
	#include "Etiquette.hpp"
	#include "Temporaire.hpp"
	#include "Programme.hpp"
	#include "Constante.hpp"
	#include "TypeUser.hpp"

//############################################### TYPES

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

//############################################### AUTRES

	#include "Expression.hpp"

	using namespace std;


	extern FILE* yyin;
	 extern char* yytext;
	extern int yylex();
	extern int yyerror(char* m);

	extern TableDesIdentificateurs* tableId;
	

	extern TableDesSymboles* tableSymb; //la table principale des symboles
	extern std::vector<TableDesSymboles*> listeTDS; // pour pouvoir stocker toutes les tables de symboles des différents contextes

	TableDesSymboles* tmpTds =  new TableDesSymboles(tableSymb->getNumContexteTSActuel(false)); //une table des symboles temporaires (pour les sous-contextes)
	extern std::vector<int> tmpNumId; //pour connaître le nombre d'identifiant d'un même type (utilisé pour remplir la TDS)

//###############################################  USERTYPE  
  
	extern TypeUser* typeUser;
	extern std::vector<TypeUser*> listeTypeUser;

//###############################################  RECORDS  

	bool nouveauRecord = true; // booleen indiquant le début de la déclaration d'un record pour pouvoir décaler d'un cran tmpTds
	int diffRecord; // 
	int numIdRecord; // numero Id du record à écrire dans la table des symboles
	bool ajoutRecord = false; // booleen servant à indiquer si le dernier type remonté est un record pour pouvoir attribuer le bon id dans la table des symboles

//############################################### EXPRESSIONS


	extern TableDesIdentificateurs* tableInteger;
	extern TableDesIdentificateurs* tableReal;
	extern TableDesIdentificateurs* tableString;
	extern TableDesIdentificateurs* tablePtr;



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
%token KW_FUNC
%token KW_PROC
%token KW_CONST

%token KW_DIV
%token KW_MOD
%token KW_AND
%token KW_OR
%token KW_XOR
%token KW_NOT

%token SEP_SCOL
%token SEP_DOT
%token SEP_DOTS
%token SEP_COMMA
%token SEP_CO
%token SEP_CF
%token SEP_DOTDOT
%token SEP_PO
%token SEP_PF

%token OP_PTR
%token OP_SUB
%token OP_EQ
%token OP_NEQ
%token OP_LTE
%token OP_MUL
%token OP_GT
%token OP_GTE
%token OP_ADD
%token OP_LT
%token OP_SLASH


%token <numeroIdent> TOK_IDENT
%token <numeroInteger> TOK_INTEGER
%token <numeroReal> TOK_REAL
%token <numeroString> TOK_STRING
%token <numeroPtr> TOK_PTR

%start Program


%type <type> Type
%type <type> BaseType
%type <typeInterval> InterType
%type <interBase> InterBase
%type <typeArray> ArrayType
%type <typePointeur> PointerType
%type <typeRecord> RecordType

%type <expression> CompExpr
%type <expression> BoolExpr
%type <expression> Expression
%type <expression> AtomExpr
%type <expression> VarExpr

%type <expression> ListDeclConst
%type <expression> DeclConst

/* Les types */

%union{

	int numeroIdent;
	int numeroInteger;
	int numeroReal;
	int numeroString;
	int numeroPtr;
	
	Type* type;
	TypeInterval* typeInterval;
	int interBase;
	TypeArray* typeArray;
	TypePointeur* typePointeur;
	TypeRecord* typeRecord;
    
	bool boolE;
	Expression* expression;

}


%%

Program         : ProgramHeader SEP_SCOL Block SEP_DOT          {}
                ;

ProgramHeader   : KW_PROGRAM TOK_IDENT                          {	
									
									listeTDS.push_back(tableSymb); // On ajoute la table des symboles principale dans listeTDS
									tableSymb->ajouter(new Programme());
								}
                ;











Block         :  BlockDeclConst BlockDeclType BlockDeclType BlockDeclVar BlockDeclFunc BlockCode		{}
              ;


BlockDeclConst : KW_CONST ListDeclConst		{
								
                                                                    for(unsigned int i = 0; i < tmpNumId.size() ; i++){
									
									cout << "const: " << i << "type: " << $2->getType()->getStringType() <<endl;
									
									tableSymb->ajouter(new Constante($2->getType(), tableSymb->getNumIdActuel(true))); 
									
																					
                                                                    }

                                                                    tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante
						}
               |
               ;

ListDeclConst  : ListDeclConst DeclConst				{ $$ = $2;}
               | DeclConst						{}
               ;

DeclConst      : TOK_IDENT OP_EQ Expression SEP_SCOL			{ 	
										cout << "TypeExpression: " << *($3->getType()->getStringType()) << endl;
										cout << "valBool: " << ($3->getValBool()) <<endl;
										cout << "valString: " << *($3->getValString()) <<endl;
										cout << "valInteger: " << ($3->getValInteger()) <<endl;
										cout << "valFloat: " << ($3->getValFloat()) <<endl;						
									   	tmpNumId.push_back($1); cout << "tok_ident" << tableId->getElement($1) << endl;
										$$ = $3;
										
									}
               ;




BlockDeclFunc : ListDeclFunc SEP_SCOL
               |
               ;

ListDeclFunc   : ListDeclFunc SEP_SCOL DeclFunc
               | DeclFunc
               ;

DeclFunc       : ProcDecl	
               | FuncDecl
               ;

ProcDecl       : ProcHeader SEP_SCOL Block		
               ;

ProcHeader     : ProcIdent	
               | ProcIdent FormalArgs
               ;

ProcIdent      : KW_PROC TOK_IDENT
               ;



FormalArgs     : SEP_PO ListFormalArgs SEP_PF
               ;

ListFormalArgs : ListFormalArgs SEP_SCOL FormalArg
               | FormalArg
               ;

FormalArg      : ValFormalArg
               | VarFormalArg
               ;

ValFormalArg   : ListIdent SEP_DOTS TOK_IDENT
               ;

VarFormalArg   : KW_VAR ListIdent SEP_DOTS TOK_IDENT
               ;


FuncDecl	:	FuncHeader SEP_SCOL Block 
		;

FuncHeader     : FuncIdent FuncResult
               | FuncIdent FormalArgs FuncResult
               ;

FuncIdent      : KW_FUNC TOK_IDENT
               ;

FuncResult     : SEP_DOTS TOK_IDENT
               ;









BlockDeclType  : KW_TYPE ListDeclType				
               |						
               ;

ListDeclType   : ListDeclType DeclType				
               | DeclType					
               ;

DeclType       : TOK_IDENT OP_EQ Type SEP_SCOL			{
									
									tableSymb->ajouter(new TypeUser(*($3), tableSymb->getNumIdActuel(true))); // On ajoute le type utilisateur dans la table des symboles actuelle
									
								}





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
									
									
									tableSymb->ajouter(new Variable($3, tableSymb->getNumIdActuel(true))); 
									}
                                                                        else{tableSymb->ajouter(new Variable($3, numIdRecord)); ajoutRecord = false;}
															
                                                                    }

                                                                    tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante						     
				

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
								
								nouveauRecord = true;
								listeTDS.push_back(tmpTds); // on rajoute le nouveau contexte dans la liste des TS
	
								
								tmpTds = new TableDesSymboles(tableSymb->getNumContexteTSActuel(true)); // on initialise tmpTds pour le nouveau contexte

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
		
			numIdRecord = tableSymb->getNumIdActuel(true);
			ajoutRecord = true;
			
			
		}

		else{diffRecord = 0;}
		
		
		for(unsigned int i = 0; i < tmpNumId.size() - diffRecord ; i++){
				
                         tmpTds->ajouter(new Variable($3, tableSymb->getNumIdActuel(true)));

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


BlockCode       : KW_BEGIN ListTest KW_END      	
                ;


ListTest        :       ListTest SEP_SCOL TOK_IDENT { cout << "\nIdentificateur: "<< tableId->getElement($3) << " | Portée: "<< tableSymb->getTableSymbContenantI(listeTDS,$3)->getPortee() << endl;}
                |       TOK_IDENT  { cout << "\nIdentificateur: "<< tableId->getElement($1) << " | Portée: "<< tableSymb->getTableSymbContenantI(listeTDS,$1)->getPortee() << endl;}
                ;


Expression     : VarExpr				{}
               | CompExpr				{}
               | AtomExpr				{}
               | BoolExpr				{/*}
               | MathExpr				{}


               ;

MathExpr       : Expression OP_ADD Expression		{ $$ = $1->opADD($1,$3); 	}
               | Expression OP_SUB Expression		{ $$ = $1->opSUB($1,$3); 	}
               | Expression OP_MUL Expression		{ $$ = $1->opMUL($1,$3); 	}
               | Expression OP_SLASH Expression		{ $$ = $1->opSLASH($1,$3);      }
               | Expression KW_DIV Expression		{ $$ = $1->opDIV($1,$3); 	}
               | Expression KW_MOD Expression		{ $$ = $1->opMOD($1,$3); 	}
               | OP_SUB Expression			{ $$ = $1->opSUB($1); 		}
               | OP_ADD Expression			{ $$ = $1->opADD($1);         */}
               ;
					
CompExpr       : Expression OP_EQ Expression		{  	
							if($1->memeType($1->getType(), $3->getType()))	{if($1 == $3){$$ = new Expression(new TypeBoolean(), true);} 
													else{$$ = new Expression(new TypeBoolean(), false);} } 
													else{cout<< "Erreur Type Comparaison EQ Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  

							cout << "TypeExpression: " << *($$->getType()->getStringType()) << endl;
							cout << "ValeurExpression: " << ($$->getValBool()) << endl;
							
							}
               | Expression OP_NEQ Expression		{ if($1->memeType($1->getType(), $3->getType()))	{if($1 != $3){$$ = new Expression(new TypeBoolean(), true);} else{$$ = new Expression(new TypeBoolean(), false);} }
							else{cout<< "Erreur Type Comparaison NEQ Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               | Expression OP_LT Expression		{ if($1->memeType($1->getType(), $3->getType()))	{if($1 < $3){$$ = new Expression(new TypeBoolean(), true);} else{$$ = new Expression(new TypeBoolean(), false);} } 
							else{cout<< "Erreur Type Comparaison LT Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               | Expression OP_LTE Expression		{ if($1->memeType($1->getType(), $3->getType()))	{if($1 <= $3){$$ = new Expression(new TypeBoolean(), true);} else{$$ = new Expression(new TypeBoolean(), false);} } 
							else{cout<< "Erreur Type Comparaison LTE Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               | Expression OP_GT Expression		{ if($1->memeType($1->getType(), $3->getType()))	{if($1 > $3){$$ = new Expression(new TypeBoolean(), true);} else{$$ = new Expression(new TypeBoolean(), false);} } 
							else{cout<< "Erreur Type Comparaison GT Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               | Expression OP_GTE Expression		{ if($1->memeType($1->getType(), $3->getType()))	{if($1 >= $3){$$ = new Expression(new TypeBoolean(), true);} else{$$ = new Expression(new TypeBoolean(), false);} } 
							else{cout<< "Erreur Type Comparaison GTE Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               ;

BoolExpr       : Expression KW_AND Expression		{ if(($1->memeType($1->getType(), $3->getType()))&& ($1->memeType($1->getType(), new string("Boolean")))) {if($1 == $3){$$ = new Expression(new TypeBoolean(), $1->getValBool());} 
																else{$$ = new Expression(new TypeBoolean(), false);} } 
							else{cout<< "Erreur Type bool AND Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}}
               | Expression KW_OR Expression		{ if(($1->memeType($1->getType(), $3->getType()))&& ($1->memeType($1->getType(), new string("Boolean")))) {if($1->getValBool() == true){$$ = new Expression(new TypeBoolean(), true);}
																 else{$$ = new Expression(new TypeBoolean(), $1->getValBool());} }
							else{cout<< "Erreur Type bool OR Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               | Expression KW_XOR Expression		{ if(($1->memeType($1->getType(), $3->getType()))&& ($1->memeType($1->getType(), new string("Boolean")))) {if($1 == $3){$$ = new Expression(new TypeBoolean(), false);} 
																else{$$ = new Expression(new TypeBoolean(), true); } }
							else{cout<< "Erreur Type bool XOR Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               | KW_NOT Expression			{ if($2->memeType($2->getType(), new string("Boolean")))	 {if($2->getValBool() == true){$$ = new Expression(new TypeBoolean(), false);} 
															  else{$$ = new Expression(new TypeBoolean(), true);} }
							else{cout<< "Erreur Type bool NOT Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  }
               ;

AtomExpr       : SEP_PO Expression SEP_PF		{$$ = $2;}
               | TOK_INTEGER				{ istringstream iss(tableInteger->getElement($1)); int nombre; $$ = new Expression(new TypeInteger(), iss >> nombre); }
               | TOK_REAL				{ istringstream iss(tableReal->getElement($1)) ; float reel; $$ = new Expression(new TypeReal(), iss >> reel);
	       
	       /*| Callfefefe
		| TOK_PTR				{cout << "tok_ptr" << $1 << endl; istringstream iss(tablePtr->getElement($1)); int pointeur; $$ = new Expression(new TypePointeur($1), iss >> pointeur); 
               | TOK_PTR				{$$ = new Expression(new TypePointeur($1), $1);} */}
               | TOK_STRING				{ $$ = new Expression(new TypeString(), new string(tableString->getElement($1))); }
               ;

VarExpr        : TOK_IDENT				{cout << "tok_ident" << $1 << endl;$$ = new Expression(tableSymb->getTableSymbContenantI(listeTDS,$1)->getSymboleI($1)->getType(), $1);/*
               | VarExpr SEP_CO Expression SEP_CF	
               | VarExpr SEP_DOT TOK_IDENT
               | VarExpr OP_PTR 
               ;

Call           : TOK_IDENT Parameters
               ;

Parameters     : SEP_PO ListParameters SEP_PF
               ;

ListParameters : ListParameters SEP_COMMA Expression
               | Expression		*/}
               ;




%%
