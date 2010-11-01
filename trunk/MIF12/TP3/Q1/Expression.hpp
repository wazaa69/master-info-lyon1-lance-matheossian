#ifndef _EXPRESSION_
#define _EXPRESSION_

#include <string>
#include "Type.hpp"

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
	
		bool getValInteger();

		bool getValFloat();

		bool getValString();

		Type* getType();
		
		bool memeType(Type* _type1, Type* _type2);

		bool memeType(Type* _type1, std::string* _type2);
};

#endif
