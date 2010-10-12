%{

    #include "lexer.hpp"

    #include "TDI.hpp"

    extern TableDesIdentificateurs* tableId;
    int column = 0;

%}

%%


program			{column += yyleng; return KW_PROGRAM;}
var                     {column += yyleng; return KW_VAR;}
begin                   {column += yyleng; return KW_BEGIN;}
end                     {column += yyleng; return KW_END;}
integer                 {column += yyleng; return KW_INTEGER;}
real                    {column += yyleng; return KW_REAL;}
boolean                 {column += yyleng; return KW_BOOLEAN;}
char                    {column += yyleng; return KW_CHAR;}
string                  {column += yyleng; return KW_STRING;}

"."                     {column += yyleng; return SEP_DOT;}
":"                     {column += yyleng; return SEP_DOTS;}
";"                     {column += yyleng; return SEP_SCOL;}
","                     {column += yyleng; return SEP_COMMA;}


"->" |
"^"                     {column += yyleng; return OP_PTR;}


[a-zA-Z][a-zA-Z0-9]*    {column += yyleng; yylval.numero = tableId->ajouter(yytext);  return TOK_IDENT;}
.			
\n

%%

int yyerror(char* message)
{
    fprintf( stderr, "Erreur d'analyse : %s [%s]\n", message, yytext );

    return 0;
}
