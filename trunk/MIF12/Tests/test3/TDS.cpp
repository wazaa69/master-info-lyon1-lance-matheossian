#include <iostream>

#include "TDS.hpp"

using namespace std;


TableDesSymboles::TableDesSymboles(){}
TableDesSymboles::~TableDesSymboles(){}

void TableDesSymboles::ajouter(Symbole* symb){tableSymb.push_back(symb);}


void TableDesSymboles::afficherTable()
{
	for (unsigned int i = 0; i < tableSymb.size(); i++)
		cout << i << " | " <<(*tableSymb[i]->getCategorie()) << " | " << *(tableSymb[i]->getType()->getStringType()) << endl;
}
