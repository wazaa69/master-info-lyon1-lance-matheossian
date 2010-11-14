#ifndef _SYMBOLE_
#define _SYMBOLE_

#include "Type.hpp"
#include <string>


/**
 * Gestion d'un symbole.
 */
class Symbole {

	protected:

        std::string* categorie; /**< categorie variable, constante, fonction, procedure, etiquette, temporaire ... */
        Type* type; /**< type  int, bool, char, ... */
	int id; /**< id identifiant du symbole */

	std::string* nomSymbole;

    public:
	
	  /**
        *   @brief Constructeur
        */
        Symbole();

        /**
        *   @brief Destructeur
        */
        ~Symbole();


        /**
	* @brief Accesseur
        *   @return Retourne une chaine de caractères qui précise la catégorie du symbole
        */
        std::string* getCategorie();

        /**
	* @brief Accesseur
        *   @return Retourne l'adresse d'une instance de la classe Type
        */
        Type* getType();

	/**
	* @brief Accesseur
        *   @return Retourne l'identifiant du symbole
        */
        int getID();

	/**
	* @brief Accesseur
        *   @return Retourne un string correspondant au nom du symbole
        */
	std::string* getNomSymbole();

};


#endif

