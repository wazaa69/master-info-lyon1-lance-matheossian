#ifndef _EXPRESSION_
#define _EXPRESSION_

#include <string>
#include "Type.hpp"
#include "TypeBoolean.hpp"

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

		Expression* comparaison(Expression* ex1, Expression* ex2, string* operation);
};

#endif
