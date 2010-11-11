#ifndef _VALEUR_
#define _VALEUR_

#include <string>
#include <string.h>
#include "Type.hpp"


/**
 * Gestion d'une valeur.
 */
class Valeur {
       
	private:

	Type* type;

	int valInt;
	float valFloat;
	bool valBool;
	std::string* valString;

	public:
	
	    /**
        *   @brief Constructeur
        */
		Valeur(Type* _type, int _valInt);

		Valeur(Type* _type, float _valFloat);
	
		Valeur(Type* _type, std::string* _valString);

		Valeur(Type* _type, bool _valBool);

	void setType(Type* _type);


        /**
        *   @brief Destructeur
        */
        ~Valeur();	

	Type* getType();

	bool getValBool();
	
	int getValInteger();

	float getValFloat();

	std::string* getValString();
	
};


#endif

