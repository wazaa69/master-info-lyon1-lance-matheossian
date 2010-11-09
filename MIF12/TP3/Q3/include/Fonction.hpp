#ifndef _FONCTION_
#define _FONCTION_

#include "Symbole.hpp"


/**
 * Gestion d'une fonction.
 */
class Fonction : public Symbole {
       
	
	private:
	
	Type* typeResult;
	int nomTDS;
	int arite;

	public:
	
	    /**
        *   @brief Constructeur, initialise la fonction avec un identifiant et le type de retour
	*   @param _id identifiant du symbole
	*   @param _typeResult type du retour
        */
        Fonction(int _id, Type* _typeResult,int _arite, int _nomTDS);
	
	int getNomTDS();

	int getArite();


        /**
        *   @brief Destructeur
        */
        ~Fonction();	
	
	
};


#endif



