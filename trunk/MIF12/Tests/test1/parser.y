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
  //  extern TableSymbole* tableSymb;



/*
    void addToTS(Symbole* sym)
    {
        // on parcourt la pile des var
        while(!g_varStack.empty())
        {
            tableSymb->add(tableId->get(g_varStack.back()),sym);
            g_varStack.pop_back();
        }
    }

    void addToTS(Type* type)
    {
        addToTS(new SymbVar(type));
    }

*/
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

%start Program

%%

Program         : ProgramHeader SEP_SCOL Block SEP_DOT          {printf("-1-\n");}
                ;

ProgramHeader   : KW_PROGRAM TOK_IDENT                          {printf("-2-\n"); tableId->ajouter(yytext);}
                ;

Block           : BlockDeclVar BlockCode                        {printf("-3-\n");}
                ;

BlockDeclVar    : KW_VAR ListDeclVar                            {printf("-4-\n"); }
                |                                               {printf("-4b-\n");}
                ;

ListDeclVar     : ListDeclVar DeclVar                           {printf("-5-\n");}
                | DeclVar                                       {printf("-6-\n");}
                ;

DeclVar         : ListIdent SEP_DOTS Type SEP_SCOL              {printf("-7-\n");}
                ;

ListIdent       : ListIdent SEP_COMMA TOK_IDENT                 { printf("-8- %s\n",yytext);}
                | TOK_IDENT                                     { printf("-9- %s\n",yytext); tableId->ajouter(yytext);}
                ;

Type            : KW_INTEGER                                    
                | KW_REAL                                       
                | KW_BOOLEAN                                    {printf("-12-\n");}
                | KW_CHAR                                       {printf("-13-\n");}
                | KW_STRING                                     
                ;

BlockCode       : KW_BEGIN ListInstr KW_END                     {printf("-15-\n");}
                ;

ListInstr       :
                                |
                                ;

%%
