
#ifndef _PROCEDURE_
#define _PROCEDURE_

#include "Symbole.hpp"
/**
 * Gestion d'une procédure
 */
class Procedure : public Symbole {
       
	
	private:

	int nomTDS; /**< nomTDS nom de la TDS */
	int arite;  /**< arite arité de la TDS */

	public:
	
	/**
        *   @brief Constructeur, initialise le symbole avec un identifiant
	*   @param _id identifiant du symbole
	*   @param _arite arité de la procédure
	*   @param _nomTDS nom de la TDS dans laquelle est déclarée la procédure
        */
        Procedure(int _id, int _arite, int _nomTDS);

	/**
        *   @brief Accesseur
	*   @return entier nomTDS
        */
	int getNomTDS();

	/**
        *   @brief Accesseur
	*   @return entier arite
        */
	int getArite();


        /**
        *   @brief Destructeur
        */
        ~Procedure();	
	
	
};


#endif

