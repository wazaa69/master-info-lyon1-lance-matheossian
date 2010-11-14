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

		bool operandeIdentifiant; /** booleen indiquand si l'opérande est un identifiant ou une simple valeur */

		Symbole* identifiant; /** symbole de l'opérande (est NULL si juste une valeur) */
		Valeur* valeur; /** valeur de l'opérande */

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
		* @param _valeur Valeur de l'opérande
		*/
		Operande(Type* _type, float _valFloat);
	
		/**
		* @brief Constructeur
		* @param _valeur Valeur de l'opérande
		*/
		Operande(Type* _type, std::string* _valString);

		/**
		* @brief Constructeur
		* @param _valeur Valeur de l'opérande
		*/
		Operande(Type* _type, bool _valBool);
		
		/**
		* @brief Destructeur
		* @param _valeur Valeur de l'opérande
		*/
		~Operande();

//################################################################################## ACCESSEURS
		
		bool getValBool();
	
		int getValInteger();

		float getValFloat();

		std::string* getValString();

		std::string getValConvString();

		Type* getType();



		Valeur* getValeur();

		Symbole* getSymbole();


//################################################################################## MUTATEURS

		void setType(Type* _type);
		
		//void setValBool(bool _valBool);


//################################################################################## METHODES

		bool isIdentifiant();


		bool memeType(Type* _type1, Type* _type2);

		bool memeType(Type* _type1, std::string* _type2);
		
		/**
		*   @brief Va evaluer le résultat de l'opération "operation" entre les Operandes ex1 et ex2
		*   @param ex1 une Operande, ex2 une Operande, operation un string
		*   @return Retourne une Operande intialisée avec le  bon type et la bonne valeur
		*/
		Operande* operation(Symbole* symboleRetour, Operande* ex1, Operande* ex2, std::string* _operation);

		/**
		*   @brief Va evaluer le résultat de la comparaison booleenne "operation" entre les Operandes ex1 et ex2
		*   @param ex1 une Operande, ex2 une Operande, operation un string
		*   @return Retourne une Operande intialisée avec un typeBoolean et la bonne valeur
		*/
		Operande* comparaisonBool(Operande* ex1, Operande* ex2, std::string* _operation);

		/**
		*   @brief Va evaluer le résultat de la comparaison "operation" entre les Operandes ex1 et ex2
		*   @param ex1 une Operande, ex2 une Operande, operation un string
		*   @return Retourne une Operande intialisée avec un typeBoolean et la bonne valeur
		*/
		Operande* comparaison(Operande* ex1, Operande* ex2, std::string* operation);
};

#endif
