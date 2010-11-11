#include "Valeur.hpp"
#include <iostream>  

using namespace std;

Valeur::Valeur(Type* _type, int _valInt){
	

	valInt = _valInt;

	valFloat = 0;
	valBool = false;
	valString = new string("_");
	type = _type;
	
}

Valeur::Valeur(Type* _type, float _valFloat){
	

	valFloat = _valFloat;

	valInt = 0;
	valBool = false;
	valString = new string("_");
	
	type = _type;
}

Valeur::Valeur(Type* _type, string* _valString){
		

	valString = _valString;
	
	valInt = 0;
	valBool = false;
	valFloat = 0;

	type = _type;
	
}

Valeur::Valeur(Type* _type, bool _valBool){
		
	valBool = _valBool;

	valInt = 0;
	valString = new string("_");
	valFloat = 0;

	type = _type;
}

Valeur::~Valeur(){}

Type* Valeur::getType(){return type;}

bool Valeur::getValBool(){return valBool;}

int Valeur::getValInteger(){return valInt;}

float Valeur::getValFloat(){return valFloat;}

string* Valeur::getValString(){return valString;}


void Valeur::setType(Type* _type){ _type = type;}
