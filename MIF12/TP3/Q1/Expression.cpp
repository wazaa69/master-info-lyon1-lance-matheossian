#include <iostream>
#include <string>
#include <string.h>
#include "Expression.hpp"

using namespace std;

//####################################### CONSTRUCTEURS

Expression::Expression(Type* _type, int _valInt){

	valInt = _valInt;

	valFloat = 0;
	valBool = false;
	valString = new string("_");
	type = _type;
	
}

Expression::Expression(Type* _type, float _valFloat){
	
	valFloat = _valFloat;

	valInt = 0;
	valBool = false;
	valString = new string("_");
	
	type = _type;
}

Expression::Expression(Type* _type, string* _valString){
	
	valString = _valString;
	
	valInt = 0;
	valBool = false;
	valFloat = 0;

	type = _type;
	
}

Expression::Expression(Type* _type, bool _valBool){
	
	valBool = _valBool;

	valInt = 0;
	valString = new string("_");
	valFloat = 0;

	type = _type;
}


//####################################### ACCESSEURS


Type* Expression::getType(){return type;}

bool Expression::getValBool(){return valBool;}

bool Expression::getValInteger(){return valInt;}

bool Expression::getValFloat(){return valFloat;}

bool Expression::getValString(){return valString;}

bool Expression::memeType(Type* _type1, Type* _type2)
{
	string* a1 =  _type1->getStringType();
	string* a2 =  _type2->getStringType();	

	return (*a1 == *a2);
}

bool Expression::memeType(Type* _type1, string* _type2)
{
	string* a1 =  _type1->getStringType();
	string* a2 =  _type2;	

	return (*a1 == *a2);
}

//####################################### DESTRUCTEUR

Expression::~Expression(){}





