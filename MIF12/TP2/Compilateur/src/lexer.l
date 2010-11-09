%{

    #include "lexer.hpp"
 
	
    #include "TDI.hpp"
    extern TableDesIdentificateurs* tableId;

    int column = 0;
    int ligne = 1;

%}

chiffre  [0-9]
chiffres  [0-9]+

TOK_IDENT   [a-zA-Z][a-zA-Z0-9]*
TOK_INTEGER {chiffres}

SEP_DOT				"."
SEP_DOTS			":"
SEP_SCOL			";"
SEP_COMMA			"," 
SEP_CO				"["
SEP_CF				"]"
SEP_DOTDOT			".."
SEP_PO				"("
SEP_PF				")"



OP_EQ				"="

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
array			{column += yyleng; return KW_ARRAY;}
of			{column += yyleng; return KW_OF;}
type			{column += yyleng; return KW_TYPE;}
record			{column += yyleng; return KW_RECORD;}
function 		{column += yyleng; return KW_FUNC;}
procedure		{column += yyleng; return KW_PROC;}

{SEP_DOT}               {column += yyleng; return SEP_DOT;}
{SEP_DOTS}              {column += yyleng; return SEP_DOTS;}
{SEP_SCOL}              {column += yyleng; return SEP_SCOL;}
{SEP_COMMA}             {column += yyleng; return SEP_COMMA;}
{SEP_CO}		{column += yyleng; return SEP_CO;}
{SEP_CF}		{column += yyleng; return SEP_CF;}
{SEP_DOTDOT}		{column += yyleng; return SEP_DOTDOT;}
{SEP_PO}		{column += yyleng; return SEP_PO;}
{SEP_PF}		{column += yyleng; return SEP_PF;}


{OP_EQ}			{column += yyleng; return OP_EQ;}
"->" |
"^"                     {column += yyleng; return OP_PTR;}
"-"			{column += yyleng; return OP_SUB;}







{TOK_INTEGER}	{ return TOK_INTEGER;}
{TOK_IDENT}    {column += yyleng; yylval.numero = tableId->ajouter(yytext);  return TOK_IDENT;}
.			
\n		{ligne += 1;}

%%

int yyerror(char* message)
{
    fprintf( stderr, "Erreur d'analyse : %s [%s] %d\n", message, yytext, ligne );

    return 0;
}
