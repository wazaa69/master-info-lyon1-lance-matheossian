#include "Constante.hpp"

using namespace std;

 Constante::Constante(Type* _type, int _id){
	categorie = new string("constante");
    	type = _type;
	id = _id;
	nomSymbole = new string("");
}

 Constante::Constante(Type* _type, int _id, string nomConstante){
	categorie = new string("constante");
    	type = _type;
	id = _id;
	nomSymbole = new string(nomConstante);
}

Constante::~Constante(){}

