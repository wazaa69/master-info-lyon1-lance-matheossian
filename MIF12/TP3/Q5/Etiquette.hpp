#ifndef _ETIQUETTE_
#define _ETIQUETTE_

#include "Symbole.hpp"
#include <string>
/**
 * Gestion d'une constante.
 */
class Etiquette : public Symbole {
       
	private: 
	
	int numeroInstruction;
	int numeroBloc;

	std::string* nomEtiquette;

	public:

	/**
        *   @brief Constructeur, initialise l'Etiquette
	*   @param _id identifiant du symbole
        */
        Etiquette(int _id);


	Etiquette(int _id, std::string _nomEtiquette);

        /**
        *   @brief Destructeur
        */
        ~Etiquette();	
	
};


#endif

