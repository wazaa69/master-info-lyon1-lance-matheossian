%{
    #include <cstdio>
    #include <iostream>

    #include "TDI.hpp"
  //  #include "TableSymb.hpp"



    extern FILE* yyin;
    extern char* yytext;
    extern int yylex();
    extern int yyerror(char* m);

    extern TableDesIdentificateurs* tableId;
    extern TableSymbole* tableSymb;
	int nbID = 0;

   vector<string> tmpNumId; // contient la liste des idents (pour la table des symboles)


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

%token TOK_IDENT


%token <numero> TOK_IDENT
%type <code> Type

%union{
	int numero;
	Type* code;
}


%start Program

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
									printf("-7-\n");
									for(int i = 0; i < tmpNumId.size()){
										tableSymb.ajouter(tmpNumId[i], "variable", $3);
										cout << $3 << " a été ajouté à la table des symboles."<<endl;}
								}
                ;

ListIdent        :    ListIdent SEP_COMMA TOK_IDENT	       {
									if($3 != null) tmpNumId.ajouter($3);
									cout << $3 << " a été ajouté à la table des identifiants."<<endl;
								}
                 |    TOK_IDENT   			       {
									if($1 != null) tmpNumId.ajouter($1);
									cout << $1 << " a été ajouté à la table des identifiants."<<endl;
								}
                 ;


Type            :    KW_INTEGER 				{$$ = new TypeInt();}
                |    KW_REAL 					{$$ = new TypeReal();}
                |    KW_BOOLEAN 				{$$ = new TypeBool();}
                |    KW_CHAR 					{$$ = new TypeChar();}
                |    KW_STRING 					{$$ = new TypeString();}
                ;

BlockCode       : KW_BEGIN ListInstr KW_END                     {printf("-15-\n");}
                ;

ListInstr       :
                ;







%%
