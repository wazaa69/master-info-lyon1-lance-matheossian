%{
    #include <cstdio>
    #include <iostream>
    #include <vector>

    using namespace std;

    #include "TDS.hpp"
    #include "Symbole.hpp"
    #include "Type.hpp"


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

%start Program


%token <numero> TOK_IDENT
%type <type> Type


/* Les types */
%union{
    int numero;
    Type* type;
}


%%

Program         : ProgramHeader SEP_SCOL Block SEP_DOT          {printf("-1-\n");}
                ;

ProgramHeader   : KW_PROGRAM TOK_IDENT                          {printf("-2-\n");}
                ;

Block           : BlockDeclVar BlockCode                        {printf("-3-\n");}
                ;

BlockDeclVar    : KW_VAR ListDeclVar                            {printf("-4-\n"); }
                |                                               {printf("-4b-\n");}
                ;

ListDeclVar     : ListDeclVar DeclVar                           {printf("-5-\n");}
                | DeclVar                                       {printf("-6-\n");}
                ;

DeclVar         : ListIdent SEP_DOTS Type SEP_SCOL
                                                                {

                                                                    for(unsigned int i = 0; i < tmpNumId.size(); i++){

                                                                        tableSymb->ajouter(new Symbole("variable", $3));

                                                                        cout << "Un symbole a été ajouté à la table des symboles." << endl;
                                                                    }

                                                                    tmpNumId.clear(); //on supprime le contenu pour la liste de déclaration suivante

                                                                }
                ;

ListIdent        :    ListIdent SEP_COMMA TOK_IDENT             {tmpNumId.push_back($3);}
                 |    TOK_IDENT                                 {tmpNumId.push_back($1);}
                 ;



Type            :    KW_INTEGER 				                {$$ = new Type("Integer");}
                |    KW_REAL 					                {$$ = new Type("Real");}
                |    KW_BOOLEAN 				                {$$ = new Type("Boolean");}
                |    KW_CHAR 					                {$$ = new Type("Char");}
                |    KW_STRING 					                {$$ = new Type("String");}
                ;


BlockCode       : KW_BEGIN ListInstr KW_END                     {printf("-15-\n");}
                ;

ListInstr       :
                ;


%%
