#include "Factory.hpp"

using namespace std;

Factory::Factory(){

}

Factory::~Factory(){}

int Factory::ajouterTemporaire(TableDesIdentificateurs* _TDI, TableDesSymboles* _TDS, string _nomTemporaire, Type* _type)
{
	int id = _TDI->ajouterAutre(_nomTemporaire);

	Temporaire* temp = new Temporaire(id, _nomTemporaire, _type);

	_TDS->ajouter(temp);
	
	return id;

}
 void Factory::ajouterEtiquette() {}
