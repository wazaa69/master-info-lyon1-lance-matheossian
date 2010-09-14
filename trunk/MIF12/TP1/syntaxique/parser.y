%{
#include <stdio.h>
%}
%token TOKEN_ACCG
%token TOKEN_ACCD
%start E
%%

E   :	TOKEN_ACCG E TOKEN_ACCD { printf("bloc + E"); }
	| TOKEN_ACCG TOKEN_ACCD	{ printf("bloc"); }
	;


%%


