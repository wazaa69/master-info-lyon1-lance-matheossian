#ifndef _CONSTANTE_
#define _CONSTANTE_

#include "Symbole.hpp"
#include <string>

/**
 * Gestion d'une constante.
 */
class Constante : public Symbole{
       
	public:

	/**
        *   @brief Constructeur, initialise la constante 
	*   @param _id identifiant du symbole
        */
        Constante(Type* _type, int _id);

	/**
        *   @brief Constructeur, initialise la constante 
	*   @param _id identifiant du symbole
	*   @param nomConstante nom de la constante
        */
	Constante(Type* _type, int _id, std::string nomConstante);

        /**
        *   @brief Destructeur
        */
        ~Constante();	
	
};


#endif

