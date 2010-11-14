#ifndef _TABLEDESSYMBOLES_
#define _TABLEDESSYMBOLES_

#include <vector>

#include "Symbole.hpp"

/**
*	@brief La table des symboles.
*/
class TableDesSymboles {

	private:

		std::vector<Symbole*> tableSymb; /**< tableSymb la table des symboles */
		int numeroContexteTS; /**< numeroContexteTS numero du contexte de la TDS */
		std::string portee; /**< portee string indiquant si la portée est globale ou locale */
		
	public:

		

//###################################################################################### CONSTRUCTEURS/DESTRUCTEURS
		
        /**
        *   @brief Constructeur
        */
		TableDesSymboles(); // constructeur appelé uniquement par la table des symboles mère (num contexte initialisé à 0)

	/**
        *   @brief Constructeur
	*   @param numContexte entier initialisant le numéro de contexte de la TS
        */
		TableDesSymboles(int numContexte);

        /**
        *   @brief Destructeur
        */
		~TableDesSymboles();


//###################################################################################### ACCESSEURS

	/**
        *   @brief Accesseur
	*   Récupère numeroContexteTS
        */
		int getNumContexteTS();

	/**
        *   @brief Accesseur
	*   Récupère le numéro d'id actuel (static)
        */
		int getNumIdActuel(bool incrementation);
	
	/**
        *   @brief Accesseur
	*   Récupère le numéro de contexte actuel (static)
        */
		int getNumContexteTSActuel(bool incrementation);
	
	/**
        *   @brief Accesseur
	*   Récupère tableSymb
        */
		std::vector<Symbole*> getTableSymb();

	/**
        *   @brief Accesseur
	*   Récupère portee
        */
		std::string getPortee();
	

	bool TableSymbContientI(TableDesSymboles* TS, int identifiantSymbole);

	/**
        *   @brief Accesseur
	*   Récupère la table des symboles contenant le symbole d'identifiant identifiantSymbole à partir du tableau de TDS listeTDS
        */
	TableDesSymboles* getTableSymbContenantI(std::vector<TableDesSymboles*> listeTDS, int identifiantSymbole);


	Symbole* getSymboleI(int identifiantSymbole);

//###################################################################################### METHODES
	
        /**
        *   @brief Mutateur
        *   @param _contexte entier contenant le numéro du contexte
        */
	void setContexteTS(int _contexte);

	void incNumIdActuel();

        /**
        *   @brief Ajoute le symble dans la liste des symboles
        *   @param symb adresse d'un symbole
        */
		void ajouter(Symbole* symb);


        /**
        *   @brief Affiche le contenu de la table
        */
	void afficherTable();
	
	 /**
        *   @brief Affiche le contenu de toutes les tables contenues dans le tableau de TS en paramètre
        *   @param listeTDS vector de TDS 
        */
	void afficherTables(std::vector<TableDesSymboles*> listeTDS);
};

#endif
