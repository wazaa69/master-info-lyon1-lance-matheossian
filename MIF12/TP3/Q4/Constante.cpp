#include "Constante.hpp"

using namespace std;

 Constante::Constante(Type* _type, int _id){
	categorie = new string("constante");
    	type = _type;
	id = _id;
}

Constante::~Constante(){}

