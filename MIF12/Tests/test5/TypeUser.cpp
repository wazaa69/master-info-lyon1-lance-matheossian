#include "TypeUser.hpp"
#include <iostream>


using namespace std;

TypeUser::TypeUser(Type* _type, string nomUserType)
{	
	//cout<< "Le typeUser " << nomUserType << " a été ajouté au tableau des typeUser" << endl;
	type = _type->getStringType();
	// il faudra gérer le cas intervalle et le cas array
	
}

TypeUser::~TypeUser(){

}


