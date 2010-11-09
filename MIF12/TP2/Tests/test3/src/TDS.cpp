#include <iostream>

#include "TDS.hpp"

using namespace std;


TableDesSymboles::TableDesSymboles()
{	
	numeroContexteTS = 0;
}

TableDesSymboles::~TableDesSymboles(){}

TableDesSymboles::TableDesSymboles(int numContexte)
{
	numeroContexteTS = numContexte;
}


int TableDesSymboles::getNumContexte()
{
	return numeroContexteTS;
}

int TableDesSymboles::getNumIdActuel()
{
	return numIdActuel;
}

int TableDesSymboles::incNumIdActuel()
{
	static int numeroId = 0;	
	
	
	numeroId++;
	numIdActuel = numeroId;
	return numeroId;
	
}

int TableDesSymboles::incNumContexteActuel()
{
	static int numeroContexte = 0;	
	
	int temp = numeroContexte;
	numeroContexte++;

	return temp;
}


std::vector<Symbole*> TableDesSymboles::getTableSymb()
{
	return tableSymb;
}

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
