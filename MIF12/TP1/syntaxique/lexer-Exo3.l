%{
#include <stdio.h>
int nbAccoladeG = 0;
int nbAccoladeD = 0;
int GFIRST = 0;
int DFIRST = 0;
char tab[1000];
int i = 0;
int erreurFO = 0;
%}

BLANCS [ \t]+
SAUTS [\n]+
ACCG "{"
ACCD "}"
CHIFFRE [0-9]
ENTIER {CHIFFRE}+
REEL -?{ENTIER}("."{ENTIER})*
OPERATEUR  "+"|"-"|"*"|"/"
CARSPE "!"|";"|"."|","|"$"
LETTRE [a-zA-Zêàéè]
MOT {LETTRE}+
PHRASE ({MOT}|{REEL}|{SAUTS}|{BLANCS}|{OPERATEUR}|{CARSPE})+
GUILLEMENT \"
EXPGUILLEMET {GUILLEMENT}{PHRASE}{GUILLEMENT}
interieurBLOC ({PHRASE}|{EXPGUILLEMET})+
BLOC {ACCG}{interieurBLOC}{ACCD}


%%

{BLOC} {printf("%s\n", yytext);}

.	{printf(".", yytext);}

%%
int yyerror ( char* m ){

	 yylex();

	
	
}
