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

		int numeroContexteTS;
		int numIdActuel;	
		
		int incNumIdActuel();
		int incNumContexteActuel();

		
		int getNumContexte();
		int getNumIdActuel();
		
		std::vector<Symbole*> getTableSymb();
        /**
        *   @brief Constructeur
        */
		TableDesSymboles(); // constructeur appelé uniquement par la table des symboles mère (num contexte initialisé à 0)

		TableDesSymboles(int numContexte);

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
	void afficherTablePrincipale();
	
	void afficherTable();

	void afficherTables(std::vector<TableDesSymboles*> listeTDS);
};

#endif
