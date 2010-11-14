#ifndef _TEMPORAIRE_
#define _TEMPORAIRE_

#include "Symbole.hpp"
#include "Operande.hpp"

#include <string>
#include "Type.hpp"

/**
 * Gestion d'une constante.
 */
class Temporaire : public Symbole  {
       
	private:
		
	
	Operande* ex; /** @var ex operande */

	public:

	/**
        *   @brief Constructeur, initialise le Temporaire
	*   @param _id identifiant du symbole
	*   @param _ex operande du symbole
        */
        Temporaire(int _id, Operande* _ex);

	/**
        *   @brief Constructeur, initialise le Temporaire
	*   @param _id identifiant du symbole
	*   @param _nomTemporaire strin nom du symbole
	*   @param _type type du symbole
        */
	Temporaire(int _id, std::string _nomTemporaire, Type* _type);

        /**
        *   @brief Accesseur
	*   @return Operande ex
        */
	Operande* getOperande();

        /**
        *   @brief Destructeur
        */
        ~Temporaire();	
	
};


#endif

