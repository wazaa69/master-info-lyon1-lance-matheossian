%{
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
%}
%start E
%token ACC_IN
%token ACC_OUT
%token CODE

%%
E : E F
  | F
  ;
F : ACC_IN E ACC_OUT
  | CODE
  ;

%%
int main (int argc, char** argv ) {
        int in; /*< Descripteur du fichier à lire. */
        
        /* Vérification arguments. */
        if(argc != 2) {
                fprintf(stderr, "Erreur : Vous devez passer une source Pascal en argument.\n");
                exit(EXIT_FAILURE);
        }
        
        /* Redirection de l'entrée standard. */
        close(0);
        in = open(argv[1], O_RDONLY);
        if(in == -1) {
		
                return EXIT_FAILURE;
        }

        /* Analyse grammaticale. */
        yyparse();
        
        return EXIT_SUCCESS;
}
