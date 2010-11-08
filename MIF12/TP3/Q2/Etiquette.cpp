#include "Etiquette.hpp"

using namespace std;

Etiquette::Etiquette(int _id){

	categorie = new string("etiquette"); 
	id = _id;
}


Etiquette::Etiquette(int _id, string _nomEtiquette){

	categorie = new string("etiquette"); 
	id = _id;
	nomEtiquette = new string(_nomEtiquette);
}


Etiquette::~Etiquette(){}


