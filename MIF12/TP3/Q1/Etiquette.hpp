#ifndef _ETIQUETTE_
#define _ETIQUETTE_

#include "Symbole.hpp"
/**
 * Gestion d'une constante.
 */
class Etiquette : public Symbole {
       
	private: 
	
	int numeroInstruction;
	int numeroBloc;

	public:

	/**
        *   @brief Constructeur, initialise l'Etiquette
	*   @param _id identifiant du symbole
        */
        Etiquette(int _id);

        /**
        *   @brief Destructeur
        */
        ~Etiquette();	
	
};


#endif

