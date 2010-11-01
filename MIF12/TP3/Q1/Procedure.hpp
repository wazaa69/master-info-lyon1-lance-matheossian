#ifndef _PROCEDURE_
#define _PROCEDURE_

#include "Symbole.hpp"
#include "TDS.hpp"
/**
 * Gestion d'une constante.
 */
class Procedure  {
        private:

	TableDesSymboles* TDSLocale;

	public:

	/**
        *   @brief Constructeur, initialise la Fonction 
	*   @param _id identifiant du symbole
        */
        Procedure(int _id);

        /**
        *   @brief Destructeur
        */
        ~Procedure();	
	
};


#endif

