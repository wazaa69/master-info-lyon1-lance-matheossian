#include "Argument.hpp"



using namespace std;



 Argument::Argument(Type* _type, int _id){
	categorie = new string("argument "); 
    	type = _type;
	id = _id;
}

  Argument::Argument(TypeUser* _typeUser, int _id){
	categorie = new string("argument "); 
    	type = _typeUser->getType();
	id = _id;
}




Argument::~Argument(){}

