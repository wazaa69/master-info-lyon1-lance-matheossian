%{
    #include <cstdio>
    #include <iostream>
    #include <vector>

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

    extern FILE* yyin;
    extern char* yytext;
    extern int yylex();
    extern int yyerror(char* m);

    extern TableDesSymboles* tableSymb;
    std::vector<int> tmpNumId; //pour connaître le nombre d'identifiant d'un même type (utilisé pour remplir la TDS)

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

%token SEP_SCOL
%token SEP_DOT
%token SEP_DOTS
%token SEP_COMMA
%token SEP_CO
%token SEP_CF
%token SEP_DOTDOT

%token OP_PTR
%token OP_SUB

%token <numero> TOK_IDENT
%token TOK_INTEGER

%start Program


%type <type> Type
%type <typeInterval> InterType
%type <interBase> InterBase

/* Les types */

%union{

    int numero;
    Type* type;
    TypeInterval* typeInterval;
    char* interBase;


}


%%

Program         : ProgramHeader SEP_SCOL Block SEP_DOT         {}
                ;

ProgramHeader   : KW_PROGRAM TOK_IDENT                          {}
                ;

Block           : BlockDeclVar BlockCode                        {}
                ;

BlockDeclVar    : KW_VAR ListDeclVar                            {}
                |                                               {}
                ;

ListDeclVar     : ListDeclVar DeclVar                           {}
                | DeclVar                                       {}
                ;

DeclVar         : ListIdent SEP_DOTS Type SEP_SCOL
                                                                {

                                                                    for(unsigned int i = 0; i < tmpNumId.size(); i++){

                                                                        tableSymb->ajouter(new Symbole("variable", $3));

                                                                        cout << *($3->getStringType()) <<  " a été ajouté à la table des symboles." << endl;
                                                                    }

                                                                    tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante

                                                                }
                ;

ListIdent        :    ListIdent SEP_COMMA TOK_IDENT             {tmpNumId.push_back($3);}
                 |    TOK_IDENT                                 {tmpNumId.push_back($1);}
                 ;



Type            :    KW_INTEGER 				                {$$ = new TypeInteger();}
                |    KW_REAL 					                {$$ = new TypeReal();}
                |    KW_BOOLEAN 				                {$$ = new TypeBoolean();}
                |    KW_CHAR 					                {$$ = new TypeChar();}
                |    KW_STRING 					                {$$ = new TypeString();}
		|    OP_PTR Type						{$$ = new TypePointeur(*$2);}
		|    InterType							{/* équivaut à $$ = $1; */}
		|    UserType							{}
                ;


               

UserType       : ArrayType


ArrayType      : KW_ARRAY SEP_CO ArrayIndex SEP_CF KW_OF Type    { /*   array[ 6 .. 10 ] of integer  ou array[ a .. b ] of real */ 

               ;						}

ArrayIndex     : TOK_IDENT					{/*  a ou  6 .. 10 */}
               | InterType


InterType      : InterBase SEP_DOTDOT InterBase             {/* 6 .. 10  ou a .. 15 ou -7 .. b  */
								 $$ = new TypeInterval($1,$3); 
							     }
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
