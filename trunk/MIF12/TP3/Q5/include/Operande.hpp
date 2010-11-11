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

/**
*	@brief Operande
*/
class Operande {

	private:


		bool operandeIdentifiant;

		/*Type* type;
		
		int valInt;
		float valFloat;
		bool valBool;
		std::string* valString;
	*/
		Symbole* identifiant; 
		Valeur* valeur;

	public:

//################################################################################## CONSTRUCTEURS

		Operande(Valeur* _valeur);

		Operande(Symbole* _identifiant, Valeur* _valeur);

		Operande(Type* _type, int _valInt);

		Operande(Type* _type, float _valFloat);
	
		Operande(Type* _type, std::string* _valString);

		Operande(Type* _type, bool _valBool);
		
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
		Operande* operation(Operande* ex1, Operande* ex2, std::string* _operation);

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
