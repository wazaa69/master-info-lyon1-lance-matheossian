#include <iostream>

#include "TDS.hpp"

using namespace std;


TableDesSymboles::TableDesSymboles(){}
TableDesSymboles::~TableDesSymboles(){}

void TableDesSymboles::ajouter(Symbole* symb){tableSymb.push_back(symb);}


void TableDesSymboles::afficherTable()
{	cout << 0 << " | " <<(*tableSymb[0]->getCategorie()) << " | "<< endl;

	for (unsigned int i = 1; i < tableSymb.size(); i++)
		cout << i << " | " <<(*tableSymb[i]->getCategorie()) << " | " << *(tableSymb[i]->getType()->getStringType()) << endl;
}
