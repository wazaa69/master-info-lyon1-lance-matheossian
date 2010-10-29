#ifndef _SYMBOLE_
#define _SYMBOLE_

#include "Type.hpp"

/**
 * Gestion d'un symbole.
 */
class Symbole {

	private:

        std::string* categorie; /** variable, constante, fonction, procedure, etiquette, temporaire ... */
        Type* type; /** int, bool, char, ... */
	int id;

    public:
	
	/**
        *   @brief Constructeur, initialise le symbole avec une catégorie
        *   @param _categorie la catégorie du symbole
        */
        Symbole(std::string _categorie);

        /**
        *   @brief Constructeur, initialise le symbole avec une catégorie et un type
        *   @param _categorie la catégorie du symbole
        *   @param _type le type du symbole
        */
        Symbole(std::string _categorie, Type* _type);

	    /**
        *   @brief Constructeur, initialise le symbole avec une catégorie et un type
        *   @param _categorie la catégorie du symbole
        *   @param _type le type du symbole
	*   @param _id identifiant du symbole
        */
        Symbole(std::string _categorie, Type* _type, int _id);

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

