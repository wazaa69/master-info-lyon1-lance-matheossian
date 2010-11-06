#include "Argument.hpp"

using namespace std;



 Argument::Argument(Type* _type, int _id){
	categorie = new string("argument "); 
    	type = _type;
	id = _id;
}



Argument::~Argument(){}

