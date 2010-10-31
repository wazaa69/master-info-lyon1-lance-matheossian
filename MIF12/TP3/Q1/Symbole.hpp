#ifndef _SYMBOLE_
#define _SYMBOLE_

#include "Type.hpp"

/**
 * Gestion d'un symbole.
 */
class Symbole {

	protected:

        std::string* categorie; /** variable, constante, fonction, procedure, etiquette, temporaire ... */
        Type* type; /** int, bool, char, ... */
	int id;

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
        *   @return Retourne une chaine de caractères qui précise la catégorie du symbole
        */
        std::string* getCategorie();

        /**
        *   @return Retourne l'adresse d'une instance de la classe Type
        */
        Type* getType();

	/**
        *   @return Retourne l'identifiant du symbole
        */
        int getID();

};


#endif

