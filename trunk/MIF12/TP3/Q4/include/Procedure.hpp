
#ifndef _PROCEDURE_
#define _PROCEDURE_

#include "Symbole.hpp"
/**
 * Gestion d'une procédure
 */
class Procedure : public Symbole {
       
	
	private:

	int nomTDS;
	int arite;

	public:
	
	    /**
        *   @brief Constructeur, initialise le symbole avec un identifiant
	*   @param _id identifiant du symbole
        */
        Procedure(int _id, int _arite, int _nomTDS);

	int getNomTDS();

	int getArite();


        /**
        *   @brief Destructeur
        */
        ~Procedure();	
	
	
};


#endif

