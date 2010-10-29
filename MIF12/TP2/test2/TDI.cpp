#include <iostream>

#include "TDI.hpp"

using namespace std;


TableDesIdentificateurs::TableDesIdentificateurs(){}
TableDesIdentificateurs::~TableDesIdentificateurs(){}


int TableDesIdentificateurs::ajouter(string id){

	cout << "Ajout de " << id << endl;

	int numElem  = getPosId(&id);

	if(numElem < 0) tableId.push_back(new string(id));  //copie

	return numElem;
}


int TableDesIdentificateurs::getPosId(string* id)
{
	int temp = -1;


	for (unsigned int i = 0; i < tableId.size(); i++)
	{
        if (*tableId[i] == *id)
            temp = i;
	}
	return temp;
}


void TableDesIdentificateurs::afficherTable()
{
	for (unsigned int i = 0; i < tableId.size(); i++)
		cout << *tableId[i] << endl;
}
