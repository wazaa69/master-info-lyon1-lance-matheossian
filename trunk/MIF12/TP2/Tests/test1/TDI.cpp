#include <iostream>

#include "TDI.hpp"

using namespace std;


TableDesIdentificateurs::TableDesIdentificateurs(){}
TableDesIdentificateurs::~TableDesIdentificateurs(){}

void TableDesIdentificateurs::ajouter(char* elem){tableId.push_back(elem);}


void TableDesIdentificateurs::afficherTable()
{
	for(unsigned int i=0; i < tableId.size(); i++)
		cout << i << "  -->  " << tableId[i] << endl;
}

