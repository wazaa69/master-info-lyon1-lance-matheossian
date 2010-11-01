#ifndef _VARIABLE_
#define _VARIABLE_

#include "Symbole.hpp"

/**
 * Gestion d'une constante.
 */
class Variable : public Symbole {
       
	public:
	
	    /**
        *   @brief Constructeur, initialise le symbole avec une catégorie et un type
        *   @param _type le type du symbole
	*   @param _id identifiant du symbole
        */
        Variable(Type* _type, int _id);


        /**
        *   @brief Destructeur
        */
        ~Variable();	
	
};


#endif

