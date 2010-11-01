#include <iostream>

#include "TDS.hpp"

using namespace std;

//############################################################### CONSTRUCTEURS/DESTRUCTEURS

TableDesSymboles::TableDesSymboles()
{	
	numeroContexteTS = 0;
	portee = string("globale");
	
}

TableDesSymboles::~TableDesSymboles(){}




TableDesSymboles::TableDesSymboles(int numContexte)
{
	numeroContexteTS = numContexte;
	portee = string("locale");
	
}

//############################################################### ACCESSEURS

int TableDesSymboles::getNumIdActuel(bool incrementation)
{	
	static int numeroId = 0;
	if(incrementation)numeroId++;
	return numeroId;
}

int TableDesSymboles::getNumContexteTSActuel(bool incrementation)
{	
	static int numeroContexte = 1;
	if(incrementation)numeroContexte++;
	return numeroContexte;
}



int TableDesSymboles::getNumContexteTS()
{		
	return numeroContexteTS;
}

vector<Symbole*> TableDesSymboles::getTableSymb()
{
	return tableSymb;
}

string TableDesSymboles::getPortee()
{
	return portee;
}


bool TableDesSymboles::TableSymbContientI(TableDesSymboles* TS, int identifiantSymbole)
{
	bool contient = false;
	
		for (unsigned i = 0; i < TS->getTableSymb().size() ;i++)
		{
			if(TS-> tableSymb[i]->getID() == identifiantSymbole)
			{
				contient = true;
			}
		}
	return contient;
}



TableDesSymboles* TableDesSymboles::getTableSymbContenantI(vector<TableDesSymboles*> listeTDS, int identifiantSymbole)
{
	TableDesSymboles* TDS_vide = NULL;
	
	for (unsigned i = 0; i < listeTDS.size() ; i++)
	{	
		if(TableSymbContientI(listeTDS[i], identifiantSymbole))
		{
			return listeTDS[i];
		}
	

	}
	
	return TDS_vide;
} 

Symbole* TableDesSymboles::getSymboleI(int identifiantSymbole)
{
	Symbole* symb_vide = NULL;	

	for (unsigned i = 0; i < tableSymb.size() ;i++)
		{
			if(tableSymb[i]->getID() == identifiantSymbole)
			{
				return tableSymb[i];
			}
		}

	return symb_vide;
}


//############################################################### MUTATEURS
/*
void TableDesSymboles::incNumIdActuel()
{
	static int numeroId = 0;	
	numeroId++;
	//numIdActuel = numeroId;
}

void TableDesSymboles::incNumContexteActuel()
{
	static int numeroContexte = 0;	
	//int temp = numeroContexte;
	numeroContexte++;
}
*/
//############################################################### METHODES


void TableDesSymboles::ajouter(Symbole* symb){tableSymb.push_back(symb);}


void TableDesSymboles::afficherTablePrincipale()
{	cout << 0 << " | " <<(*tableSymb[0]->getCategorie()) << " | "<< endl;

	for (unsigned int i = 1; i < tableSymb.size(); i++)
		cout << tableSymb[i]->getID() << " | " <<(*tableSymb[i]->getCategorie()) << " | " << *(tableSymb[i]->getType()->getStringType()) << endl;
}


void TableDesSymboles::afficherTable()
{	
	

	for (unsigned int i = 0; i < tableSymb.size(); i++)
		cout << tableSymb[i]->getID() << " | " <<(*tableSymb[i]->getCategorie()) << " | " << *(tableSymb[i]->getType()->getStringType()) << endl;
}


void TableDesSymboles::afficherTables(std::vector<TableDesSymboles*> listeTDS)
{
	for (unsigned int i = 0; i < listeTDS.size(); i++)
	{
		cout << "Table Des Symboles " << listeTDS[i]->numeroContexteTS << endl;
		
		if(listeTDS[i]->numeroContexteTS == 0) { listeTDS[i]-> afficherTablePrincipale();}
		else{listeTDS[i]->afficherTable();}
		cout << endl;
	}

}


