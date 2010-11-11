#include "Fonction.hpp"

using namespace std;

 Fonction::Fonction(int _id, Type* _typeResult, int _arite, int _nomTDS){
	categorie = new string("fonction"); //copie
	id = _id;
	type = _typeResult; // on utilise type pour stocker le type de retour
	nomTDS = _nomTDS;
	arite = _arite;
	nomSymbole = new string("");
}

 int Fonction::getNomTDS(){ return nomTDS;}

 int Fonction::getArite(){ return arite;}

Fonction::~Fonction(){}


