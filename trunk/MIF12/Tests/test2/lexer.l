%{

    #include "lexer.hpp"

    #include "TDI.hpp"

    extern TableDesIdentificateurs* tableId;

%}

%%

program			        {return KW_PROGRAM;}
var                     {return KW_VAR;}
begin                   {return KW_BEGIN;}
end                     {return KW_END;}
integer                 {return KW_INTEGER;}
real                    {return KW_REAL;}
boolean                 {return KW_BOOLEAN;}
char                    {return KW_CHAR;}
string                  {return KW_STRING;}

"."                     {return SEP_DOT;}
":"                     {return SEP_DOTS;}
";"                     {return SEP_SCOL;}
","                     {return SEP_COMMA;}


[a-zA-Z][a-zA-Z0-9]*    {yylval.numero = tableId->ajouter(yytext);  return TOK_IDENT;}
.
\n

%%

int yyerror(char* message)
{
    fprintf( stderr, "Erreur d'analyse : %s [%s]\n", message, yytext );

    return 0;
}
