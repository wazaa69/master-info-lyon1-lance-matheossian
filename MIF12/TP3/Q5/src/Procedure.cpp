#include "Procedure.hpp"

using namespace std;

 Procedure::Procedure(int _id, int _arite, int _nomTDS){
	categorie = new string("procedure"); //copie
   
	id = _id;
	nomTDS = _nomTDS;
	arite = _arite;
	type = NULL;
}


 int Procedure::getNomTDS(){ return nomTDS;}

 int Procedure::getArite(){ return arite;}


Procedure::~Procedure(){}


