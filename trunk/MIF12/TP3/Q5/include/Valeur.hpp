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

	Type* type; /**< type type de la valeur */

	int valInt; /**< valInt valeur entière de la valeur */
	float valFloat; /**< valFloat valeur reele de la valeur */
	bool valBool; /**< valBool valeur booleene de la valeur */
	std::string* valString; /**< valString valeur string de la valeur */

	public:
	
	/**
        *   @brief Constructeur
	*   @param _type type de la valeur
        */
	Valeur(Type* _type);
		
	/**
        *   @brief Constructeur
	*   @param _type type de la valeur
	*   @param _valInt entier
        */
	Valeur(Type* _type, int _valInt);

	/**
        *   @brief Constructeur
	*   @param _type type de la valeur
	*   @param _valFloat reel
        */
	Valeur(Type* _type, float _valFloat);
	
	/**
        *   @brief Constructeur
	*   @param _type type de la valeur
	*   @param _valString string
        */
	Valeur(Type* _type, std::string* _valString);

	/**
        *   @brief Constructeur
	*   @param _type type de la valeur
	*   @param _valBool bool
        */
	Valeur(Type* _type, bool _valBool);



        /**
        *   @brief Destructeur
        */
        ~Valeur();	

        /**
        *   @brief Accesseur
	* @return type 
        */
	Type* getType();

        /**
        *   @brief Accesseur
	* @return valBool 
        */
	bool getValBool();
	
        /**
        *   @brief Accesseur
	* @return valInteger
        */
	int getValInteger();

        /**
        *   @brief Accesseur
	* @return valFloat
        */
	float getValFloat();

        /**
        *   @brief Accesseur
	* @return valString
        */
	std::string* getValString();

	/**
        *   @brief Mutateur
	*   @param _type type de la valeur
        */
	void setType(Type* _type);

	
};


#endif

