#ifndef _TableDesIdentificateurs_
#define _TableDesIdentificateurs_

#include <vector>
#include <string>

/**
*	@brief La table des identifiants.
*/
class TableDesIdentificateurs {

	private :

	std::vector<std::string*> tableId; /** @param tableId la table des identifiants */

	public:

        /**
        *   @brief Constructeur
        */
	TableDesIdentificateurs();

		/**
        *   @brief Destructeur
        */
	~TableDesIdentificateurs();


        /**
        *   @brief Ajoute un identifiant dans la liste des identifiants
        *   @param id un identifiant
        *   @return Retourne la position de l'identifiant ajouté
        */
	int ajouter(std::string id);

		/**
		*   @brief Recherche si un identifiant existe dans la TDI
		*   @param id un identifiant
		*   @return Retourne la position de l'identifiant si il existe dans la table, -1 sinon
		*/
	int getPosId(std::string* id);


	std::string getElement(int posId);


        /**
        *   \brief Affiche le contenu de la table
        */
	void afficherTable();


	int ajouterInteger(std::string integer);
};

#endif
