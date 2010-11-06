#ifndef _VARIABLE_
#define _VARIABLE_

#include "Symbole.hpp"
#include "TypeUser.hpp"

/**
 * Gestion d'une variable.
 */
class Variable : public Symbole {
       
	public:
	
	    /**
        *   @brief Constructeur, initialise le symbole avec une catégorie et un type
        *   @param _type le type du symbole
	*   @param _id identifiant du symbole
        */
        Variable(Type* _type, int _id);

	Variable(TypeUser* _typeUser, int _id);
        /**
        *   @brief Destructeur
        */
        ~Variable();	
	
};


#endif

