#ifndef _TEMPORAIRE_
#define _TEMPORAIRE_

#include "Symbole.hpp"
#include "Expression.hpp"

/**
 * Gestion d'une constante.
 */
class Temporaire : public Symbole  {
       
	private:
		
	Expression* ex;

	public:

	/**
        *   @brief Constructeur, initialise le Temporaire
	*   @param _id identifiant du symbole
        */
        Temporaire(int _id, Expression* _ex);

	Expression* getExpression();

        /**
        *   @brief Destructeur
        */
        ~Temporaire();	
	
};


#endif

