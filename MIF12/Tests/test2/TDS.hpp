#ifndef _TableDesSymboles_
#define _TableDesSymboles_

#include <vector>

#include "Symbole.hpp"

/**
*	@brief La table des symboles.
*/
class TableDesSymboles {

	private:

		std::vector<Symbole*> tableSymb; /** @param tableSymb la table des symboles */

	public:


        /**
        *   @brief Constructeur
        */
		TableDesSymboles();

        /**
        *   @brief Destructeur
        */
		~TableDesSymboles();

        /**
        *   @brief Ajoute le symble dans la liste des symboles
        *   @param symb adresse d'un symbole
        */
		void ajouter(Symbole* symb);


        /**
        *   @brief Affiche le contenu de la table
        */
		void afficherTable();
};

#endif
