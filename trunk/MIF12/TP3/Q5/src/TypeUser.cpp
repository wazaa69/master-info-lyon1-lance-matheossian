#include "TypeUser.hpp"
#include <iostream>


using namespace std;


TypeUser::TypeUser(Type& _type, int _id)
{
	
	cout << "Etape1 " << *_type.getStringType() << endl;

	if (*_type.getStringType() != "Array")
	{
		type = new Type(_type.getStringType());
	}
	else
	{
	cout << "Etape2 " << endl;
		TypeArray* ta = static_cast<TypeArray*>(&_type);
		type = new TypeArray(ta);
	}
	
	// gérer le cas ou on copie un typArray dans le typeUser pour afficher toutes les infos
	
	id = _id;
	categorie = new string("  type   ");

}

Type* TypeUser::getType(){ return type;}


TypeUser::~TypeUser(){

}


