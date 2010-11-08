#ifndef _TEMPORAIRE_
#define _TEMPORAIRE_

#include "Symbole.hpp"
#include "Expression.hpp"

#include <string>
#include "Type.hpp"

/**
 * Gestion d'une constante.
 */
class Temporaire : public Symbole  {
       
	private:
		
	std::string* nomTemporaire;
	Expression* ex;

	public:

	/**
        *   @brief Constructeur, initialise le Temporaire
	*   @param _id identifiant du symbole
        */
        Temporaire(int _id, Expression* _ex);

	Temporaire(int _id, std::string _nomTemporaire, Type* _type);

	Expression* getExpression();

        /**
        *   @brief Destructeur
        */
        ~Temporaire();	
	
};


#endif

