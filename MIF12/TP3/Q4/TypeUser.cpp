#include "TypeUser.hpp"
#include <iostream>


using namespace std;


TypeUser::TypeUser(Type& _type, int _id)
{
	type = new Type(_type.getStringType());
	id = _id;
	categorie = new string("  type   ");

}

Type* TypeUser::getType(){ return type;}


TypeUser::~TypeUser(){

}


