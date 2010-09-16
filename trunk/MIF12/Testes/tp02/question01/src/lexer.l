%{

#include "TableDesIdentificateurs.hpp"

//Attention, tableId est utilisé dans un fichier externe
extern TableDesIdentificateurs* tableId;

%}

%%


[a-zA-Z][a-zA-Z0-9]*	{tableId->ajouter(yytext);}
.			;
\n			;

%%

//Traiter les messages d'erreur
int yyerror ( char* message )
{
    fprintf( stderr, "Erreur d'analyse : %s [%s]\n", message, yytext );

    return 0;
}
