#include <iostream>
#include <string>
#include <string.h>
#include "Expression.hpp"

using namespace std;

//####################################### CONSTRUCTEURS

Expression::Expression(Type* _type, int _valInt){

	valInt = _valInt;
	type = _type;
	//cout << "essai " << valInt << endl;
}

Expression::Expression(Type* _type, float _valFloat){
	
	valFloat = _valFloat;
	type = _type;
}

Expression::Expression(Type* _type, string* _valString){
	
	valString = _valString;
	type = _type;
	cout << "essai " << valString << endl;
}

Expression::Expression(Type* _type, bool _valBool){
	
	valBool = _valBool;
	type = _type;
}


//####################################### ACCESSEURS


Type* Expression::getType(){return type;}

bool Expression::getValBool(){return valBool;}

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





