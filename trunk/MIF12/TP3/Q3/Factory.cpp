#include "Factory.hpp"

using namespace std;

Factory::Factory(){

}

Factory::~Factory(){}

int Factory::ajouterTemporaire(TableDesIdentificateurs* _TDI, TableDesSymboles* _TDS, string* _nomTemporaire, Type* _type)
{

	int id = _TDI->ajouterAutre(*_nomTemporaire);

	Temporaire* temp = new Temporaire(id, *_nomTemporaire, _type);
	
	_TDS->getNumIdActuel(true);
	
	_TDS->ajouter(temp);

	return id;

}

int Factory::ajouterEtiquette(TableDesIdentificateurs* _TDI, TableDesSymboles* _TDS, string* _nomEtiquette)
{

	int id = _TDI->ajouterAutre(*_nomEtiquette);

	Etiquette* temp = new Etiquette(id, *_nomEtiquette);
	
	_TDS->getNumIdActuel(true);
	
	_TDS->ajouter(temp);

	return id;

}
