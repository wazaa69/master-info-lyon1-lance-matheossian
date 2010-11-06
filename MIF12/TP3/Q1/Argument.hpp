#ifndef _ARGUMENT_
#define _ARGUMENT_

#include "Symbole.hpp"

/**
 * Gestion d'un argument.
 */
class Argument : public Symbole {
       
	public:
	
	    /**
        *   @brief Constructeur, initialise l'argument avec une catégorie et un type
        *   @param _type le type du symbole
	*   @param _id identifiant du symbole
        */
        Argument(Type* _type, int _id);


        /**
        *   @brief Destructeur
        */
        ~Argument();	
	
};


#endif

