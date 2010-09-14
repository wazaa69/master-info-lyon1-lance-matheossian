%{
#include <stdio.h>
int sautLigne=1;
int caract=0;
int mots=0;
int blancs = 0;
%}

LETTRE   [a-zA-Z]
MOT      {LETTRE}+

%%

\n	 sautLigne++;
{MOT}	mots++;
.	caract++;

%%
 int main(int argc, char *argv[]){
	 yyin=fopen(argv[1],"r");
	 yylex();
	fclose(yyin);

	printf("\nNombre de lignes : %d \n",sautLigne);
	printf("Nombre de caracteres : %d \n",caract);	
	printf("Nombre de mots : %d \n",mots);

 }
