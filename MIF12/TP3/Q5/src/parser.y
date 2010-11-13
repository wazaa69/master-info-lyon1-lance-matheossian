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

	#include "Valeur.hpp"
	#include "Symbole.hpp"
	#include "Variable.hpp"
	#include "Fonction.hpp"
	#include "Procedure.hpp"
	#include "Etiquette.hpp"
	#include "Temporaire.hpp"
	#include "Programme.hpp"
	#include "Constante.hpp"
	#include "TypeUser.hpp"
	#include "Argument.hpp"

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

	#include "Factory.hpp"
	#include "Operande.hpp"
	#include "Instruction.hpp"
	#include "ConteneurCode.hpp"

	using namespace std;

	extern bool erreur;
	extern FILE* yyin;
	extern char* yytext;
	extern int yylex();
	extern int yyerror(char* m);

	extern TableDesIdentificateurs* tableId;
	

	extern TableDesSymboles* tableSymb;
	extern std::vector<TableDesSymboles*> listeTDS; // pour pouvoir stocker toutes les tables de symboles des différents contextes

	TableDesSymboles* tmpTds =  new TableDesSymboles(0); //une table des symboles temporaires (pour les différents contextes)
	extern std::vector<int> tmpNumId; //pour connaître le nombre d'identifiant d'un même type (utilisé pour remplir la TDS)
	extern std::vector<Type*> tmpType;
	
	Factory* usine;

//###############################################  USERTYPE  
  
	extern TypeUser* typeUser;
	extern std::vector<TypeUser*> listeTypeUser;

	TypeUser* symbTypeUserRemonte;
	bool remonteeTypeUser = false;

	unsigned int numTDS_TypeRemonte;
//###############################################  RECORDS  

	TableDesSymboles* tmpRecord = new TableDesSymboles(0);

	bool nouveauRecord = true; // booleen indiquant le début de la déclaration d'un record pour pouvoir décaler d'un cran tmpTds
	int diffRecord; // 
	int numIdRecord; // numero Id du record à écrire dans la table des symboles
	bool ajoutRecord = false; // booleen servant à indiquer si le dernier type remonté est un record pour pouvoir attribuer le bon id dans la table des symboles

//############################################### OperandeS


	extern TableDesIdentificateurs* tableInteger;
	extern TableDesIdentificateurs* tableReal;
	extern TableDesIdentificateurs* tableString;
	extern TableDesIdentificateurs* tablePtr;
	extern TableDesIdentificateurs* tableBoolean;

//############################################### FONCTIONS/PROCEDURES

	unsigned int TDS_Actuelle = 0; // contient le numéro de la TS actuelle
	unsigned int niveauTDS = 0; // contient le niveau actuel de la TS (0 pour la TS principale, 1 pour celles déclarées dans cette TS, puis 2 etc)
	extern std::vector<int> tabTDSPere; // contient les numeros des TDS en fonction du niveauTDS

	unsigned int ariteArgFoncProc = 0;
	int passage = 0;

//############################################### ARGUMENTS

	extern std::vector<Argument*> tabArguments;


//############################################### TEMPORAIRES

	extern std::vector<Temporaire*> tabTemporaires;
	int nombreTemp = 0;

//############################################### CODE 3@

	 ConteneurCode* CCode = new ConteneurCode();


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
%token KW_LABEL

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
%token SEP_COMG
%token SEP_COMD

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
%token OP_AFFECT


%token <numeroIdent> TOK_IDENT
%token <numeroInteger> TOK_INTEGER
%token <numeroReal> TOK_REAL
%token <numeroString> TOK_STRING
%token <numeroPtr> TOK_PTR
%token <numeroBoolean> TOK_BOOLEAN

%start Program


%type <type> Type
%type <type> BaseType

%type <typeInterval> InterType
%type <typeInterval> ArrayIndex


%type <typeArray> ArrayType
%type <entier> InterBase
%type <entier> NSInterBase

%type <typePointeur> PointerType
%type <type> UserType

%type <typeRecord> RecordType

%type <type> FuncResult

%type <operande> CompExpr
%type <operande> BoolExpr
%type <operande> Expression
%type <operande> AtomExpr
%type <operande> VarExpr
%type <operande> MathExpr

%type <operande> ListDeclConst
%type <operande> DeclConst

/* Les types */

%union{

	int numeroIdent;
	int numeroInteger;
	int numeroReal;
	int numeroString;
	int numeroPtr;
	int numeroBoolean;
	
	Type* type;

	TypeInterval* typeInterval;

	int entier;

	TypeArray* typeArray;
	TypePointeur* typePointeur;
	TypeRecord* typeRecord;
    
	bool boolE;
	Operande* operande;

}


%left OP_ADD OP_SUB
%left OP_MUL OP_DIV
/*
%left PLUS  MOINS
%left FOIS  DIVISE
%left NEG
%right  PUISSANCE
*/
%%

//############################################################################################################################### PROG

Program         : ProgramHeader SEP_SCOL Block SEP_DOT          {}
                ;

ProgramHeader   : KW_PROGRAM TOK_IDENT                          {	
									TDS_Actuelle = 0;
									tabTDSPere.push_back(TDS_Actuelle);
									listeTDS.push_back(tmpTds); // On ajoute la table des symboles principale dans listeTDS
									tmpTds->ajouter(new Programme());
								}
                ;





Block         :  BlockDeclConst BlockDeclType BlockDeclType BlockDeclVar BlockDeclFunc BlockCode		{}
              ;

//############################################################################################################################### BLOCK CONST

BlockDeclConst : KW_CONST ListDeclConst		{
								int tempNumTds;
								 tempNumTds = TDS_Actuelle;
								
                                                                    for(unsigned int i = 0; i < tmpType.size() ; i++){

									 listeTDS[tempNumTds]->ajouter(new Constante(tmpType[i], tableSymb->getNumIdActuel(true))); 
																
                                                                    }
                                                                    tmpType.clear(); //on supprime le contenu pour la liste de déclaration suivante
						}
               |
               ;

ListDeclConst  : ListDeclConst DeclConst				{ $$ = $2;}
               | DeclConst						{}
               ;

DeclConst      : TOK_IDENT OP_EQ Expression SEP_SCOL			{ 	

										// ici on pourrait créer une instruction 3@ pour récupérer le code
						
									   	tmpType.push_back($3->getType());
										$$ = $3;
										
									}
               ;


//############################################################################################################################### BLOCK FUNC

BlockDeclFunc : ListDeclFunc SEP_SCOL			{}
               |
               ;

ListDeclFunc   : ListDeclFunc SEP_SCOL DeclFunc			
               | DeclFunc					
               ;

DeclFunc       : ProcDecl					{ niveauTDS--;  TDS_Actuelle = tabTDSPere[niveauTDS];}	
								
               | FuncDecl					{ niveauTDS--;  TDS_Actuelle = tabTDSPere[niveauTDS];}
								
               ;

ProcDecl       : ProcHeader SEP_SCOL Block			
               ;

ProcHeader     : ProcIdent					{

								
						
								if (niveauTDS >= tabTDSPere.size()) 
									{ tabTDSPere.push_back(TDS_Actuelle);}
								else { tabTDSPere[niveauTDS] = TDS_Actuelle;}


								listeTDS[TDS_Actuelle]->ajouter(new Procedure(tableSymb->getNumIdActuel(true)-ariteArgFoncProc, ariteArgFoncProc, TDS_Actuelle));

								TDS_Actuelle = tableSymb->getNumContexteTSActuel(true);

								tmpTds = new TableDesSymboles(TDS_Actuelle); // on initialise tmpTds pour le nouveau contexte
								listeTDS.push_back(tmpTds); // on rajoute le nouveau contexte dans la liste des TS
									for(unsigned int i = 0; i < tabArguments.size() ; i++)
								{
									listeTDS[TDS_Actuelle]->ajouter(tabArguments[i]);	
								}

								tabArguments.clear();

								ariteArgFoncProc = 0; // on remet l'arité à 0 pour la prochaine déclaration de fonc/proc
								niveauTDS++;


								}
               | ProcIdent FormalArgs				{

								
								

								if (niveauTDS >= tabTDSPere.size()) 
									{ tabTDSPere.push_back(TDS_Actuelle);}
								else { tabTDSPere[niveauTDS] = TDS_Actuelle;}


								listeTDS[TDS_Actuelle]->ajouter(new Procedure(tableSymb->getNumIdActuel(true)-ariteArgFoncProc, ariteArgFoncProc, TDS_Actuelle));
								
								TDS_Actuelle = tableSymb->getNumContexteTSActuel(true);
								
								tmpTds = new TableDesSymboles(TDS_Actuelle); // on initialise tmpTds pour le nouveau contexte
								listeTDS.push_back(tmpTds); // on rajoute le nouveau contexte dans la liste des TS
				
								for(unsigned int i = 0; i < tabArguments.size() ; i++)
								{
									listeTDS[TDS_Actuelle]->ajouter(tabArguments[i]);	
								}

								tabArguments.clear();
								ariteArgFoncProc = 0; // on remet l'arité à 0 pour la prochaine déclaration de fonc/proc
								niveauTDS++;



								 }
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

ValFormalArg   : ListIdent SEP_DOTS Type		{ 
							
							tableSymb->incNumIdActuel();
							for (unsigned int i = 0; i < tmpNumId.size() ; i++)
							{								
								 ariteArgFoncProc++;
								if (remonteeTypeUser == false)
								{		
									tabArguments.push_back(new Argument($3, tableSymb->getNumIdActuel(true)+1));
									
								}
				
								else 
								{
									// on vérifie que le typeUser de l'argument est bien défini dans la TS principale
									if (numTDS_TypeRemonte == 0){
									tabArguments.push_back(new Argument(symbTypeUserRemonte, tableSymb->getNumIdActuel(true)+1));
									}
									else { std::cerr << "Erreur : Le type de l'argument de la fonc/proc n'est pas défini dans la TS principale \n"; erreur = true; return 0;}
								}
							}
						
							remonteeTypeUser = false;
							tmpNumId.clear();}
               ;

VarFormalArg   : KW_VAR ListIdent SEP_DOTS Type         { 
							
							tableSymb->incNumIdActuel();
							for (unsigned i = 0; i < tmpNumId.size() ; i++)
							{	
								ariteArgFoncProc++; 
								if (remonteeTypeUser == false)
								{	
									
									tabArguments.push_back(new Argument($4, tableSymb->getNumIdActuel(true)+1));
									
								}
				
								else 
								{	// on vérifie que le typeUser de l'argument est bien défini dans la TS principale
									if (numTDS_TypeRemonte == 0){
									tabArguments.push_back(new Argument(symbTypeUserRemonte, tableSymb->getNumIdActuel(true)+1));
									}
									else { std::cerr << "Erreur : Le type de l'argument de la fonc/proc n'est pas défini dans la TS principale \n"; erreur = true; return 0;}
								}
							}
						
							remonteeTypeUser = false;
							tmpNumId.clear();}
               ;


FuncDecl       : FuncHeader SEP_SCOL Block 
	       ;


FuncHeader     : FuncIdent FuncResult			 {
								
												
								if (niveauTDS >= tabTDSPere.size()) 
									{ tabTDSPere.push_back(TDS_Actuelle);}
								else { tabTDSPere[niveauTDS] = TDS_Actuelle;}


								listeTDS[TDS_Actuelle]->ajouter(new Fonction(tableSymb->getNumIdActuel(true)-ariteArgFoncProc, $2,ariteArgFoncProc, TDS_Actuelle));
								TDS_Actuelle = tableSymb->getNumContexteTSActuel(true);
								
								tmpTds = new TableDesSymboles(TDS_Actuelle); // on initialise tmpTds pour le nouveau contexte
								listeTDS.push_back(tmpTds); // on rajoute le nouveau contexte dans la liste des TS
	
								
								
								for(unsigned int i = 0; i < tabArguments.size() ; i++)
								{
									listeTDS[TDS_Actuelle]->ajouter(tabArguments[i]);	
								}

								tabArguments.clear();
								ariteArgFoncProc = 0; // on remet l'arité à 0 pour la prochaine déclaration de fonc/proc
								niveauTDS++;


		

							  }
               | FuncIdent FormalArgs FuncResult	 { 

				
								if (niveauTDS >= tabTDSPere.size()) 
									{ tabTDSPere.push_back(TDS_Actuelle);}
								else { tabTDSPere[niveauTDS] = TDS_Actuelle;}

	
								listeTDS[TDS_Actuelle]->ajouter(new Fonction(tableSymb->getNumIdActuel(true)-ariteArgFoncProc, $3,ariteArgFoncProc, TDS_Actuelle));
								TDS_Actuelle = tableSymb->getNumContexteTSActuel(true);

								
							        tmpTds = new TableDesSymboles(TDS_Actuelle); // on initialise tmpTds pour le nouveau contexte
								listeTDS.push_back(tmpTds); // on rajoute le nouveau contexte dans la liste des TS
			
							

								for(unsigned int i = 0; i < tabArguments.size() ; i++)
								{
									listeTDS[TDS_Actuelle]->ajouter(tabArguments[i]);	
								}

								tabArguments.clear();							
								ariteArgFoncProc = 0; // on remet l'arité à 0 pour la prochaine déclaration de fonc/proc
								niveauTDS++;

							}

							   
               ;

FuncIdent      : KW_FUNC TOK_IDENT			 {}	
               ;

FuncResult     : SEP_DOTS Type				{ $$ = $2; /* on remonte le type de retour de la fonction */}
               ;






//############################################################################################################################### BLOCK TYPE


BlockDeclType  : KW_TYPE ListDeclType				
               |						
               ;

ListDeclType   : ListDeclType DeclType				
               | DeclType					
               ;

DeclType       : TOK_IDENT OP_EQ Type SEP_SCOL			{
									int tempNumTds;
									if (TDS_Actuelle == 0){ tempNumTds = 0;} else { tempNumTds = TDS_Actuelle;}
									listeTDS[tempNumTds]->ajouter(new TypeUser(*($3), tableSymb->getNumIdActuel(true))); // On ajoute le type utilisateur dans la table des symboles actuelle
									
								}


//############################################################################################################################### BLOCK VAR


BlockDeclVar    : KW_VAR ListDeclVar                            {}
                |                                               {}
                ;

ListDeclVar     : ListDeclVar DeclVar                           {}
                | DeclVar                                       {}
                ;

DeclVar         : ListIdent SEP_DOTS Type SEP_SCOL
                                                                {

									for(unsigned int i = 0; i < tmpNumId.size() ; i++){
										if (remonteeTypeUser == false)
										{		
											if (!ajoutRecord){ listeTDS[TDS_Actuelle]->ajouter(new Variable($3, tableSymb->getNumIdActuel(true),tableId->getElement(tableSymb->getNumIdActuel(false)))); }
												
												
						                                        else{listeTDS[TDS_Actuelle]->ajouter(new Variable($3, numIdRecord,tableId->getElement(tableSymb->getNumIdActuel(false)))); ajoutRecord = false;}
										}
										else
										{
											
											
											// on vérifie que le typeUser de l'argument est bien défini dans la TS principale
											if ((numTDS_TypeRemonte == 0) || (numTDS_TypeRemonte == TDS_Actuelle)){
												listeTDS[TDS_Actuelle]->ajouter(new Variable(symbTypeUserRemonte, tableSymb->getNumIdActuel(true),tableId->getElement(tableSymb->getNumIdActuel(false))));
											}
											else { std::cerr << "Erreur : Le type de la variable définie dans la fonc/proc n'est pas défini dans la TS principale \n"; erreur = true; return 0;}
										}
														
									}
									remonteeTypeUser = false;
									tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante						     
				

                                                                }
		| KW_LABEL TOK_IDENT SEP_SCOL			{
									listeTDS[TDS_Actuelle]->ajouter(new Etiquette(tableSymb->getNumIdActuel(true)));
									
								}
                ;


ListIdent        :    ListIdent SEP_COMMA TOK_IDENT             {tmpNumId.push_back($3);}
                 |    TOK_IDENT                                 {tmpNumId.push_back($1);}
                 ;






//############################################################################################################################### TYPE


Type            :    TOK_IDENT							{ 	
											remonteeTypeUser = true;
											
											symbTypeUserRemonte = static_cast<TypeUser*>(tableSymb->getTableSymbContenantI(listeTDS,$1)->getSymboleI($1));
											numTDS_TypeRemonte = tableSymb->getTableSymbContenantI(listeTDS,$1)->getNumContexteTS();
								
										/* il faut analyser le tokident pour savoir s'il correspond a un type défini et le remonter  */}	                
		|    UserType							{$$ = $1;}
		|    BaseType							{$$ = $1;}
                ;

BaseType	:    KW_INTEGER 				                {$$ = new TypeInteger();}
                |    KW_REAL 					                {$$ = new TypeReal();}
                |    KW_BOOLEAN 				                {$$ = new TypeBoolean();}
                |    KW_CHAR 					                {$$ = new TypeChar();}
                |    KW_STRING 							{$$ = new TypeString();}
               

UserType       :    ArrayType							{$$ = $1;}
	       |    InterType							{$$ = $1;}
	       |    PointerType							{$$ = $1;}
	       |    RecordType							{ niveauTDS--; TDS_Actuelle = tabTDSPere[niveauTDS];  $$ = $1;}
	       ;		

//############################################################################################################################### TYPE RECORD

RecordType     : KW_RECORD RecordFields KW_END					{

										
								if (niveauTDS >= tabTDSPere.size())  
									{ tabTDSPere.push_back(TDS_Actuelle);} 
								else { tabTDSPere[niveauTDS] = TDS_Actuelle;} 

								if(tabTDSPere.size() == 1) TDS_Actuelle++;

								TypeRecord* tmpRec = new TypeRecord(tmpRecord);
							
								
																
								tmpNumId.push_back(0);
							        $$ = tmpRec; // on remonte le record
								
								nouveauRecord = true;
	
								TDS_Actuelle = tableSymb->getNumContexteTSActuel(true);
								tmpRecord->setContexteTS(TDS_Actuelle);

								tmpTds = new TableDesSymboles(TDS_Actuelle);  
								listeTDS.push_back(tmpRecord); // on rajoute le nouveau contexte dans la liste des TS

								tmpRecord = new TableDesSymboles(0);

								//tmpTds = new TableDesSymboles(tableSymb->getNumContexteTSActuel(true)); // on initialise tmpTds pour le nouveau contexte

								niveauTDS++;  

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
			 
			 
                         tmpRecord->ajouter(new Variable($3, tableSymb->getNumIdActuel(true) , tableId->getElement(tableSymb->getNumIdActuel(false))));

                   }

                tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante
}




//############################################################################################################################### TYPE ARRAY


ArrayType      : KW_ARRAY SEP_CO ArrayIndex SEP_CF KW_OF Type    { /*   array[ 6 .. 10 ] of integer  ou array[ a .. b ] of real */ 
								   $$ = new TypeArray($3,$6);
               ;						 }

ArrayIndex     : TOK_IDENT					{}
               | InterType					{}


InterType      : InterBase SEP_DOTDOT InterBase             {
								
								 $$ = new TypeInterval($1,$3); 
							     }
               ;

PointerType    : OP_PTR Type			{$$ = new TypePointeur(*$2);}
               ;



InterBase      : NSInterBase			{}
               | OP_SUB NSInterBase		{}
               ;
NSInterBase    : TOK_IDENT			{	// On récupère le string correspondant dans la TS des Tok_Ident et on le cast en entier  
							istringstream iss(tableId->getElement($1)); int nombre; 
						        iss >> nombre; $$ = nombre;}
               | TOK_INTEGER			{	// On récupère le string correspondant dans la TS des Tok_ident et on le cast en entier  
						       	istringstream iss(tableInteger->getElement($1)); 
							int nombre;  iss >> nombre; $$ = nombre;}
               ;

//############################################################################################################################### INSTRUCTION

BlockCode       : KW_BEGIN ListInstr KW_END  { }    	
                ;


ListInstr      : ListInstr SEP_SCOL Instr
               | Instr
               ;


Instr          : /*KW_WHILE Expression KW_DO Instr
               | KW_REPEAT ListInstr KW_UNTIL Expression
               | KW_FOR TOK_IDENT OP_AFFECT Expression ForDirection Expression KW_DO Instruction
               | KW_IF Expression KW_THEN Instruction
               | KW_IF Expression KW_THEN Instruction KW_ELSE Instruction
               | */VarExpr OP_AFFECT Expression	  
							{ 
								   $1 = $1->operation($1->getSymbole(),$1,$3, new string(":="));
		    						   CCode->ajouterInstFinBlocCourant(new Instruction($1, $3, NULL, 1, new Etiquette(tableSymb->getNumContexteTSActuel(true), ""))); 
							}
               | //Call
               | BlockCode
	       | Commentaire
               ;

Commentaire	: SEP_COMG TOK_STRING SEP_COMD // ne marche pas (je pense que le SEP_COMD n'est pas pris en compte par le lexer qui le voit comme un composant de TOK_STRING
		;

/*
ForDirection   : KW_TO
               | KW_DOWNTO
               ;
*/



//############################################################################################################################### OPERANDE


Expression     : VarExpr				{}
               | CompExpr				{}
               | AtomExpr				{}
               | BoolExpr				{}
               | MathExpr				{}


               ;

//############################################################################################################################### OPERANDE OPERATIONS

MathExpr       : Expression OP_ADD Expression	{
						if (($1 != NULL) && ($3 != NULL)){ 
							// création du temporaire qui va être utilisé pour l'initialisation de l'opérande contenant le résultat de l'opération
							nombreTemp++;
 							ostringstream oss;	 oss << nombreTemp; // on convertit le numéro du temporaire en string pour pouvoir créer son nom
							string nomTemp = "temp" + oss.str();

							int idTemp = usine->ajouterTemporaire(tableId, listeTDS[TDS_Actuelle],new string(nomTemp), new TypeInteger());
							Symbole* s1 = listeTDS[TDS_Actuelle]->getSymboleI(idTemp);

							// Initialisation des composantes de l'instruction et du bloc d'instructions
							Operande* op1 = new Operande(s1,NULL);
							Etiquette* e1 = new Etiquette(tableSymb->getNumContexteTSActuel(true), "");
							Instruction* i1 = new Instruction(op1,$1,$3,2,e1);

							op1 = $1->operation(s1,$1,$3,new string("+"));  	 // on effectue l'addition des 2 opérandes et on stocke le résultat dans op1
							CCode->ajouterInstFinBlocCourant(i1); $$ = op1;  // on ajoute l'instruction dans le bloc d'instructions
							
							
						}
						else { 	std::cerr << "Erreur : addition avec une opérande nulle  \n"; erreur = true; return 0;}

							
						}


               | Expression OP_SUB Expression	{ 
						if (($1 != NULL) && ($3 != NULL)){ 
							// création du temporaire qui va être utilisé pour l'initialisation de l'opérande contenant le résultat de l'opération
							nombreTemp++;
 							ostringstream oss;	 oss << nombreTemp; // on convertit le numéro du temporaire en string pour pouvoir créer son nom
							string nomTemp = "temp" + oss.str();

							int idTemp = usine->ajouterTemporaire(tableId, listeTDS[TDS_Actuelle],new string(nomTemp), new TypeInteger());
							Symbole* s1 = listeTDS[TDS_Actuelle]->getSymboleI(idTemp);

							// Initialisation des composantes de l'instruction et du bloc d'instructions
							Operande* op1 = new Operande(s1,NULL);
							Etiquette* e1 = new Etiquette(tableSymb->getNumContexteTSActuel(true), "");
							Instruction* i1 = new Instruction(op1,$1,$3,3,e1);

							op1 = $1->operation(s1,$1,$3,new string("-"));  	 // on effectue la soustraction des 2 opérandes et on stocke le résultat dans op1
							CCode->ajouterInstFinBlocCourant(i1); $$ = op1;  // on ajoute l'instruction dans le bloc d'instructions
							
							
						}
						else { 	std::cerr << "Erreur : soustraction avec une opérande nulle  \n"; erreur = true; return 0;}


}
               | Expression OP_MUL Expression	{   
						if (($1 != NULL) && ($3 != NULL)){ 
							// création du temporaire qui va être utilisé pour l'initialisation de l'opérande contenant le résultat de l'opération
							nombreTemp++;
 							ostringstream oss;	 oss << nombreTemp; // on convertit le numéro du temporaire en string pour pouvoir créer son nom
							string nomTemp = "temp" + oss.str();

							int idTemp = usine->ajouterTemporaire(tableId, listeTDS[TDS_Actuelle],new string(nomTemp), new TypeInteger());
							Symbole* s1 = listeTDS[TDS_Actuelle]->getSymboleI(idTemp);

							// Initialisation des composantes de l'instruction et du bloc d'instructions
							Operande* op1 = new Operande(s1,NULL);
							Etiquette* e1 = new Etiquette(tableSymb->getNumContexteTSActuel(true), "");
							Instruction* i1 = new Instruction(op1,$1,$3,4,e1);

							op1 = $1->operation(s1,$1,$3,new string("*"));  	 // on effectue la multiplication des 2 opérandes et on stocke le résultat dans op1
							CCode->ajouterInstFinBlocCourant(i1); $$ = op1;  // on ajoute l'instruction dans le bloc d'instructions
							
							
						}
						else { 	std::cerr << "Erreur : multiplication avec une opérande nulle  \n"; erreur = true; return 0;}




}
               | Expression OP_SLASH Expression	{ 
						if (($1 != NULL) && ($3 != NULL)){ 
							// création du temporaire qui va être utilisé pour l'initialisation de l'opérande contenant le résultat de l'opération
							nombreTemp++;
 							ostringstream oss;	 oss << nombreTemp; // on convertit le numéro du temporaire en string pour pouvoir créer son nom
							string nomTemp = "temp" + oss.str();

							int idTemp = usine->ajouterTemporaire(tableId, listeTDS[TDS_Actuelle],new string(nomTemp), new TypeInteger());
							Symbole* s1 = listeTDS[TDS_Actuelle]->getSymboleI(idTemp);

							// Initialisation des composantes de l'instruction et du bloc d'instructions
							Operande* op1 = new Operande(s1,NULL);
							Etiquette* e1 = new Etiquette(tableSymb->getNumContexteTSActuel(true), "");
							Instruction* i1 = new Instruction(op1,$1,$3,5,e1);

							op1 = $1->operation(s1,$1,$3,new string("/"));  	 // on effectue la division des 2 opérandes et on stocke le résultat dans op1
							CCode->ajouterInstFinBlocCourant(i1); $$ = op1;  // on ajoute l'instruction dans le bloc d'instructions

						}
						else { 	std::cerr << "Erreur : multiplication avec une opérande nulle  \n"; erreur = true; return 0;}
}
               | Expression KW_DIV Expression	{
						if (($1 != NULL) && ($3 != NULL)){ 
							// création du temporaire qui va être utilisé pour l'initialisation de l'opérande contenant le résultat de l'opération
							nombreTemp++;
 							ostringstream oss;	 oss << nombreTemp; // on convertit le numéro du temporaire en string pour pouvoir créer son nom
							string nomTemp = "temp" + oss.str();

							int idTemp = usine->ajouterTemporaire(tableId, listeTDS[TDS_Actuelle],new string(nomTemp), new TypeInteger());
							Symbole* s1 = listeTDS[TDS_Actuelle]->getSymboleI(idTemp);

							// Initialisation des composantes de l'instruction et du bloc d'instructions
							Operande* op1 = new Operande(s1,NULL);
							Etiquette* e1 = new Etiquette(tableSymb->getNumContexteTSActuel(true), "");
							Instruction* i1 = new Instruction(op1,$1,$3,6,e1);

							op1 = $1->operation(s1,$1,$3,new string("div"));  	 // on effectue l'opération DIV des 2 opérandes et on stocke le résultat dans op1
							CCode->ajouterInstFinBlocCourant(i1); $$ = op1;  // on ajoute l'instruction dans le bloc d'instructions

						}
						else { 	std::cerr << "Erreur : operation DIV avec une opérande nulle  \n"; erreur = true; return 0;}
  }
               | Expression KW_MOD Expression	{  
						if (($1 != NULL) && ($3 != NULL)){ 
							// création du temporaire qui va être utilisé pour l'initialisation de l'opérande contenant le résultat de l'opération
							nombreTemp++;
 							ostringstream oss;	 oss << nombreTemp; // on convertit le numéro du temporaire en string pour pouvoir créer son nom
							string nomTemp = "temp" + oss.str();

							int idTemp = usine->ajouterTemporaire(tableId, listeTDS[TDS_Actuelle],new string(nomTemp), new TypeInteger());
							Symbole* s1 = listeTDS[TDS_Actuelle]->getSymboleI(idTemp);

							// Initialisation des composantes de l'instruction et du bloc d'instructions
							Operande* op1 = new Operande(s1,NULL);
							Etiquette* e1 = new Etiquette(tableSymb->getNumContexteTSActuel(true), "");
							Instruction* i1 = new Instruction(op1,$1,$3,7,e1);

							op1 = $1->operation(s1,$1,$3,new string("mod"));  	 // on effectue l'opération MOD des 2 opérandes et on stocke le résultat dans op1
							CCode->ajouterInstFinBlocCourant(i1); $$ = op1;  // on ajoute l'instruction dans le bloc d'instructions

						}
						else { 	std::cerr << "Erreur : operation MOD avec une opérande nulle  \n"; erreur = true; return 0;}
}

               | OP_SUB Expression		{

						if ($2 != NULL){ 

							// Initialisation des composantes de l'instruction et du bloc d'instructions
							Symbole* s1 = $2->getSymbole();
							Operande* op1;
							if (s1 != NULL)  op1 = new Operande($2->getSymbole(),$2->getValeur());
							else   op1 = new Operande($2->getValeur());

							Etiquette* e1 = new Etiquette(tableSymb->getNumContexteTSActuel(true), "");
							Instruction* i1 = new Instruction(op1,NULL,$2,8,e1);
					
							op1 = $2->operation($2->getSymbole(),$2,NULL,new string("-a"));  	 // on effectue l'opération NEG des 2 opérandes et on stocke le résultat dans op1
							CCode->ajouterInstFinBlocCourant(i1); $$ = op1;  // on ajoute l'instruction dans le bloc d'instructions

						}
						else { 	std::cerr << "Erreur : operation NEG avec une opérande nulle  \n"; erreur = true; return 0;} /*}}
               | OP_ADD Expression		{ $$ = $2->operation($2,NULL,new string("+a")); CCode->ajouterInstFinBlocCourant(new Instruction($$, NULL, $2, 9, new Etiquette(tableSymb->getNumContexteTSActuel(true), ""))); */}
               ;
				
//  $$ = $2->operation($2,NULL,new string("-a")); CCode->ajouterInstFinBlocCourant(new Instruction($$, NULL, $2, 8, new Etiquette(tableSymb->getNumContexteTSActuel(true), "")));
	
//############################################################################################################################### OPERANDE COMPARAISONS

CompExpr       : Expression OP_EQ Expression	{ $$ = $1->comparaison($1,$3, new string("="));  }
               | Expression OP_NEQ Expression	{ $$ = $1->comparaison($1,$3, new string("<>")); }
               | Expression OP_LT Expression	{ $$ = $1->comparaison($1,$3, new string("<"));  }
               | Expression OP_LTE Expression	{ $$ = $1->comparaison($1,$3, new string("<=")); }
               | Expression OP_GT Expression	{ $$ = $1->comparaison($1,$3, new string(">"));  }
               | Expression OP_GTE Expression	{ $$ = $1->comparaison($1,$3, new string(">=")); }
               ;

BoolExpr       : Expression KW_AND Expression	{ $$ = $1->comparaisonBool($1,$3, new string("and"));   }
               | Expression KW_OR Expression	{ $$ = $1->comparaisonBool($1,$3, new string("or"));    }
               | Expression KW_XOR Expression	{ $$ = $1->comparaisonBool($1,$3, new string("xor"));   }
               | KW_NOT Expression		{ $$ = $2->comparaisonBool($2,NULL, new string("not")); }
               ;

AtomExpr       : SEP_PO Expression SEP_PF		{$$ = $2;}
               | TOK_INTEGER				{ istringstream iss(tableInteger->getElement($1)); 
							  int nombre;  iss >> nombre; $$ = new Operande(new TypeInteger(),nombre); }
	       | TOK_BOOLEAN				{
							  string booleen = tableBoolean->getElement($1);
							 if((booleen.substr(0,1) == "t" )|| (booleen.substr(0,1) == "T" )){ $$ = new Operande(new TypeBoolean(), true);}
							  else{ $$ = new Operande(new TypeBoolean(), false); }
					
						
							 }
               | TOK_REAL				{ istringstream iss(tableReal->getElement($1)) ; 
							  float reel; iss >> reel; $$ = new Operande(new TypeReal(), reel);
	       
	       /*| Call
		| TOK_PTR				{} 
               | TOK_PTR				{} */}
               | TOK_STRING				{ $$ = new Operande(new TypeString(), new string(tableString->getElement($1))); }
               ;

VarExpr        : TOK_IDENT				{ 	// il faut récupérer la dernière valeur correspondant à l'ident
											

								Operande* op1 = CCode->getDerniereAffectationVariable(tableId->getElement($1));
	
								// cas ou la variable a déjà été affectée
								if (op1 != NULL) { $$ = op1; }

								else if (op1 == NULL) { 	
									Valeur* valTOK_IDENT = new Valeur(tableSymb->getTableSymbContenantI(listeTDS,$1)->getSymboleI($1)->getType()); 

									Operande* opRetour = new Operande(tableSymb->getTableSymbContenantI(listeTDS,$1)->getSymboleI($1), valTOK_IDENT);

									if(opRetour->getSymbole() != NULL){ 
										$$ = opRetour;
										}
									else { 	std::cerr << "Erreur : Il n'existe aucun symbole dans la TDS ayant pour identifiant " << $1 << " \n"; erreur = true; return 0;}

											

								}
							

								//

								//
								//cout << "opretour " << opRetour->getValConvString() << endl;
								// Vérification de l'existence de VarExpr 
								
								
							
							
							
/*
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
