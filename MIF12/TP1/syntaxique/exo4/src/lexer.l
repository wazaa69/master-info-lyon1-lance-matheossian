%{
#include "parser.h"
/** Fonction affichant une erreur ? */
int yyerror(char*);
%}
%%
\{	return ACC_IN;
\}	return ACC_OUT;
(.|\n)	return CODE;
%%
int yyerror(char* m) {
	printf("%s [%s]", m, yytext);

	return 0;
}
