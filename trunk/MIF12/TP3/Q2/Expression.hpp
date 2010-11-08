#ifndef _EXPRESSION_
#define _EXPRESSION_

#include <string>
#include "Type.hpp"
#include "TypeBoolean.hpp"
#include "TypeInteger.hpp"
#include "TypeString.hpp"
#include "TypePointeur.hpp"
#include "TypeReal.hpp"

/**
*	@brief Expression
*/
class Expression {

	private:

		Type* type;
		
		int valInt;
		float valFloat;
		bool valBool;
		std::string* valString; 

	public:

		Expression(Type* _type, int _valInt);

		Expression(Type* _type, float _valFloat);
	
		Expression(Type* _type, std::string* _valString);

		Expression(Type* _type, bool _valBool);
		
		~Expression();
		
		bool getValBool();
	
		int getValInteger();

		float getValFloat();

		std::string* getValString();

		Type* getType();

		void setValBool(bool _valBool);
		
		bool memeType(Type* _type1, Type* _type2);

		bool memeType(Type* _type1, std::string* _type2);
		
		/**
		*   @brief Va evaluer le résultat de l'opération "operation" entre les expressions ex1 et ex2
		*   @param ex1 une Expression, ex2 une Expression, operation un string
		*   @return Retourne une expression intialisée avec le  bon type et la bonne valeur
		*/
		Expression* operation(Expression* ex1, Expression* ex2, std::string* _operation);

		/**
		*   @brief Va evaluer le résultat de la comparaison booleenne "operation" entre les expressions ex1 et ex2
		*   @param ex1 une Expression, ex2 une Expression, operation un string
		*   @return Retourne une expression intialisée avec un typeBoolean et la bonne valeur
		*/
		Expression* comparaisonBool(Expression* ex1, Expression* ex2, std::string* _operation);

		/**
		*   @brief Va evaluer le résultat de la comparaison "operation" entre les expressions ex1 et ex2
		*   @param ex1 une Expression, ex2 une Expression, operation un string
		*   @return Retourne une expression intialisée avec un typeBoolean et la bonne valeur
		*/
		Expression* comparaison(Expression* ex1, Expression* ex2, std::string* operation);
};

#endif
