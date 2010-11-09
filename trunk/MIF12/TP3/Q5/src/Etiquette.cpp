#include "Etiquette.hpp"

using namespace std;

Etiquette::Etiquette(int _id){

	categorie = new string("etiquette"); 
	id = _id;

	static int _numeroInstruction = 0;
	numeroInstruction = _numeroInstruction;
	_numeroInstruction++;
}


Etiquette::Etiquette(int _id, string _nomEtiquette){

	categorie = new string("etiquette"); 
	id = _id;
	nomEtiquette = new string(_nomEtiquette);

	static int _numeroInstruction = 0;
	numeroInstruction = _numeroInstruction;
	_numeroInstruction++;
}


Etiquette::~Etiquette(){}


