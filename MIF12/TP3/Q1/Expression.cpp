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

int Expression::getValInteger(){return valInt;}

float Expression::getValFloat(){return valFloat;}

string* Expression::getValString(){return valString;}


void Expression::setValBool(bool _valBool){valBool = _valBool;}






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

Expression* Expression::comparaison(Expression* ex1, Expression* ex2, string* operation)
{
	switch(ex1->getType()->getStringType())
	{
		case "Boolean":
			switch(operation)
			{
			case "<":
			break;
			case ">":
			break;
			case: "<="
			break;
			case: ">="
			break;
			case
		break;
		case "Integer":

		break;
		case "Real":

		break;
		case "String":

		break;
		case "Pointeur":

		break;
		default:
		break;
	}

	Expression* exRetour = new Expression(new TypeBoolean(), false);
	return exRetour;
}

/*
	if($1->memeType($1->getType(), $3->getType()))	{if($1 == $3){$$ = new Expression(new TypeBoolean(), true);} 
	else{$$ = new Expression(new TypeBoolean(), false);} } 
	else{cout<< "Erreur Type Comparaison EQ Dans Decl Const" << endl; $$ = new Expression(new TypeBoolean(), false);}  

cout << "TypeExpression: " << *($$->getType()->getStringType()) << endl;
cout << "ValeurExpression: " << ($$->getValBool()) << endl;

*/


//####################################### DESTRUCTEUR

Expression::~Expression(){}





