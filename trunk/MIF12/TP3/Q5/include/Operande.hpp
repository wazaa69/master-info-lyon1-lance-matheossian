#ifndef _Operande_
#define _Operande_



#include "Symbole.hpp"

#include <string>
#include <string.h>
#include <sstream>

#include "Type.hpp"
#include "TypeBoolean.hpp"
#include "TypeInteger.hpp"
#include "TypeString.hpp"
#include "TypePointeur.hpp"
#include "TypeReal.hpp"

//#include "Variable.hpp"
//#include "Temporaire.hpp"

#include "Valeur.hpp"
extern bool erreur;

/**
*	@brief Operande
*/
class Operande {

	private:

		bool operandeIdentifiant; /**< operandeIdentifiant booleen indiquand si l'opérande est un identifiant ou une simple valeur */

		Symbole* identifiant; /**< identifiant symbole de l'opérande (est NULL si juste une valeur) */
		Valeur* valeur; /**< valeur valeur de l'opérande */

	public:

//################################################################################## CONSTRUCTEURS

		/**
		* @brief Constructeur
		* @param _valeur Valeur de l'opérande
		*/
		Operande(Valeur* _valeur);

		/**
		* @brief Constructeur
		* @param _identifiant symbole de l'opérande
		* @param _valeur Valeur de l'opérande
		*/
		Operande(Symbole* _identifiant, Valeur* _valeur);

		/**
		* @brief Constructeur
		* @param _type type de la valeur de l'opérande
		* @param _valInt entier intitialisant la valeur de l'opérande
		*/
		Operande(Type* _type, int _valInt);

		/**
		* @brief Constructeur
		* @param _type type de la valeur de l'opérande
		* @param _valFloat reel intitialisant la valeur de l'opérande
		*/
		Operande(Type* _type, float _valFloat);
	
		/**
		* @brief Constructeur
		* @param _type type de la valeur de l'opérande
		* @param _valString string intitialisant la valeur de l'opérande
		*/
		Operande(Type* _type, std::string* _valString);

		/**
		* @brief Constructeur
		* @param _type type de la valeur de l'opérande
		* @param _valBool booleen intitialisant la valeur de l'opérande
		*/
		Operande(Type* _type, bool _valBool);
		
		/**
		* @brief Destructeur
		*/
		~Operande();

//################################################################################## ACCESSEURS
		
		/**
		* @brief Accesseur
		* @return valbool de valeur
		*/
		bool getValBool();
	
		/**
		* @brief Accesseur
		* @return valInteger de valeur
		*/
		int getValInteger();

		/**
		* @brief Accesseur
		* @return valFloat de valeur
		*/
		float getValFloat();

		/**
		* @brief Accesseur
		* @return valString de valeur
		*/
		std::string* getValString();

		/**
		* @brief Accesseur
		* @return un string de la valeur de la variable correspondant à l'initialisation du type de valeur
		*/
		std::string getValConvString();

		/**
		* @brief Accesseur
		* @return le type de valeur
		*/
		Type* getType();


		/**
		* @brief Accesseur
		* @return valeur
		*/
		Valeur* getValeur();

		/**
		* @brief Accesseur
		* @return symbole
		*/
		Symbole* getSymbole();


//################################################################################## MUTATEURS

		/**
		* @brief Mutateur
		*   @param _type 
		*/
		void setType(Type* _type);
		

//################################################################################## METHODES

		/**
		* @brief indique si l'opérande est un identifiant
		* @return booleen
		*/
		bool isIdentifiant();

		/**
		* @brief vérifie si les types sont du même type
		* @param _type1 type
		* @param _type2 type
		* @return booleen
		*/
		bool memeType(Type* _type1, Type* _type2);

		/**
		* @brief vérifie si les types sont du même type
		* @param _type1 type 
		* @param _type2 string
		* @return booleen
		*/
		bool memeType(Type* _type1, std::string* _type2);
		
		/**
		*   @brief Va evaluer le résultat de l'opération "operation" entre les Operandes ex1 et ex2
		*	@param symboleRetour le symbole de retour
		*   @param ex1 première opérande
		*   @param ex2 seconde opérande
		*	@param _operation string représentant l'opération
		*   @return Retourne une Operande intialisée avec le  bon type et la bonne valeur
		*/
		Operande* operation(Symbole* symboleRetour, Operande* ex1, Operande* ex2, std::string* _operation);

		/**
		*   @brief Va evaluer le résultat de la comparaison booleenne "operation" entre les Operandes ex1 et ex2
		*   @param ex1 première opérande
		*   @param ex2 seconde opérande
		*	@param _operation string représentant l'opération
		*   @return Retourne une Operande intialisée avec un typeBoolean et la bonne valeur
		*/
		Operande* comparaisonBool(Operande* ex1, Operande* ex2, std::string* _operation);

		/**
		*   @brief Va evaluer le résultat de la comparaison "operation" entre les Operandes ex1 et ex2
		*   @param ex1 première opérande
		*   @param ex2 seconde opérande
		*	@param _operation string représentant l'opération
		*   @return Retourne une Operande intialisée avec un typeBoolean et la bonne valeur
		*/
		Operande* comparaison(Operande* ex1, Operande* ex2, std::string* _operation);
};

#endif
