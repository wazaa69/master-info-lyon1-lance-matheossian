#include "TypePointeur.hpp"


using namespace std;

TypePointeur::TypePointeur(const Type &ref)
{
	type = new string("Pointeur");
	reference = new Type(ref);
	
}

TypePointeur::~TypePointeur(){}

/*
string* TypePointeur::getStringType(){
	return new string (*type + " , " );
}
*/

//+ *(reference.type)


/*
Type* TypePointeur::getReference() const {
        return _reference;
}
*/
