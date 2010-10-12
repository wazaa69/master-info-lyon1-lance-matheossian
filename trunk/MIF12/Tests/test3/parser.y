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

%token SEP_SCOL
%token SEP_DOT
%token SEP_DOTS
%token SEP_COMMA

%token OP_PTR

%start Program


%token <numero> TOK_IDENT

%type <type> Type


/* Les types */
%union{
    int numero;
    Type* type;
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

                ;


BlockCode       : KW_BEGIN ListInstr KW_END                     {}
                ;

ListInstr       :
                ;



%%
