%{
#include "TableDesIdentificateurs.hpp"

%}

%%

[a-zA-Z][a-zA-Z0-9]*    /* ajouter l'identificateur à la table */
.                       ;
\n                      ;

%%

int main ( int argc, char** argv )
{
    yyin = fopen ( argv [ 1 ], "r" );
    yylex ();
    /* afficher la table des identificateurs */
    fclose ( yyin );
} 
