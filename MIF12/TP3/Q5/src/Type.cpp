#include <iostream>
#include <string>

#include "Type.hpp"

using namespace std;

Type::Type(){type=NULL;}

Type::Type(string* _type){
    type = _type; //copie
}


Type::Type(const Type &_type){	
	type =  new string("");
	type = _type.type;
}


Type::~Type(){
	//std::cout << getStringType() << std::endl; //on a bien toutes les adresses
	//if(type != NULL) std::cout << *getStringType() << std::endl; //mais pas les string
	//delete type; //soucis
}


string* Type::getStringType(){return type;}


