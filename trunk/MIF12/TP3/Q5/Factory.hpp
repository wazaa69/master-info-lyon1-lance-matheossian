#ifndef _FACTORY_
#define _FACTORY_

#include <iostream>
#include "TDS.hpp"
#include "TDI.hpp"
#include "Etiquette.hpp"
#include "Temporaire.hpp"

 class Factory {

	private:
	
	Factory();
	~Factory();

	public:

	static int ajouterTemporaire(TableDesIdentificateurs* _TDI, TableDesSymboles* _TDS, std::string* _nomTemporaire, Type* _type);
	
	static int ajouterEtiquette(TableDesIdentificateurs* _TDI, TableDesSymboles* _TDS, std::string* _nomEtiquette);
	
	


};

#endif
