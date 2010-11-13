#include <iostream>
#include <string>

#include "Type.hpp"

using namespace std;

Type::Type(){}

Type::Type(string* _type){
    type = _type; //copie
}


Type::Type(const Type &_type){	
	type =  new string("");
	type = _type.type;
}


Type::~Type(){}


string* Type::getStringType(){return type;}


