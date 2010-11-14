#ifndef _TableDesIdentificateurs_
#define _TableDesIdentificateurs_

#include <vector>
#include <string>

/**
*	@brief La table des identifiants.
*/
class TableDesIdentificateurs {

	private :

	std::vector<std::string*> tableId; /**< tableId la table des identifiants */

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
	int ajouterIdent(std::string id);

	/**
	*   @brief Recherche si un identifiant existe dans la TDI
	*   @param id un identifiant
	*   @return Retourne la position de l'identifiant si il existe dans la table, -1 sinon
	*/
	int getPosId(std::string* id);

	/**
	*   @brief Récupère l'élément de tableId à la position en paramètre
	*   @param posId position de l'identifiant dans la tableId
	*   @return string élément de tableId  
	*/
	std::string getElement(int posId);


        /**
        *   @brief Affiche le contenu de la table
        */
	void afficherTable();


	/**
        *   @brief permet d'ajouter un element autre que TOK_ident dans un tableId
	*  @param id string 
	* @return entier idenfiant de l'élément dans le tableau
	*  
        */
	int ajouterAutre(std::string id);


};

#endif
