%{
#include <stdio.h>
#include "parser.h"
int yyerror (char*);
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
ACCG \{
ACCD \}
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


{ACCG}	{ printf("accpG"); return TOKEN_ACCG;}
{ACCD}	{return TOKEN_ACCD;}


%%

int main(int argc, char** argv)
{
	
	yyin=fopen(argv[1],"r");
	yyparse ();
	fclose(yyin);
}



