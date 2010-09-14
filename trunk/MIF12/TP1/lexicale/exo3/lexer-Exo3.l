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
 

%%
"{"	{
		nbAccoladeG++; 
		if ((GFIRST == 0) && (DFIRST == 0))GFIRST = 1;

		 tab[i] = '{'; printf("\n%c",tab[i]); i++;
	
		if (nbAccoladeG < nbAccoladeD) erreurFO = 1;

	}

"}"	{
		nbAccoladeD++;
		 if ((GFIRST == 0) && (DFIRST == 0))DFIRST = 1;
		 tab[i] = '}'; printf("		%c",tab[i]); i++;
		if (nbAccoladeG < nbAccoladeD) erreurFO = 1;

	}


%%
 int main(int argc, char *argv[]){
	 yyin=fopen(argv[1],"r");
	 yylex();
	fclose(yyin);
	
	

	if(erreurFO == 1) printf("\nMauvaise imbrication des accolades -> Fermeture avant ouverture !" );

	else if (DFIRST == 1) printf("\nMauvaise imbrication des accolades -> 1ere accolade fermée !" );
	else if (nbAccoladeG < nbAccoladeD) printf("\nMauvaise imbrication des accolades -> Il manque des accolades d'ouverture.");
	else if (nbAccoladeG > nbAccoladeD) printf("\nMauvaise imbrication des accolades -> Il manque des accolades de fermeture.");
	else printf("Bonne imbrication des accolades.");
	printf("\nNombre d'accolades gauche: %d", nbAccoladeG);
	printf("\nNombre d'accolades droite: %d\n", nbAccoladeD);
}
