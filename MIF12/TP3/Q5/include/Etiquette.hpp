#ifndef _ETIQUETTE_
#define _ETIQUETTE_

#include "Symbole.hpp"
#include <string>
/**
 * Gestion d'une étiquette.
 */
class Etiquette : public Symbole {
       
	private: 
	
	int numeroInstruction; /** < numeroInstruction numéro correspondant à l'instruction */
 
	public:

	/**
        *   @brief Constructeur, initialise l'Etiquette
	*   @param _id identifiant du symbole
        */
        Etiquette(int _id);


	/**
        *   @brief Constructeur, initialise l'Etiquette
	*   @param _id identifiant du symbole
	*   @param _nomEtiquette string contenant le nom de l'étiquette
        */
	Etiquette(int _id, std::string _nomEtiquette);

        /**
        *   @brief Destructeur
        */
        ~Etiquette();	
	
};


#endif

