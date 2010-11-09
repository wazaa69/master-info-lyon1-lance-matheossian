#include <iostream>

#include "TDI.hpp"

using namespace std;


TableDesIdentificateurs::TableDesIdentificateurs(){}
TableDesIdentificateurs::~TableDesIdentificateurs(){}


int TableDesIdentificateurs::ajouter(string id){

	cout << "Ajout de " << id << endl;

	int numElem  = getPosId(&id);
	
	 if(numElem != -1)tableId.push_back(new string(id));  //copie
	
	cout << "taille TDI " <<tableId.size() << endl;
	
	
	return numElem;
}


int TableDesIdentificateurs::getPosId(string* id)
{
	int temp = tableId.size();


	for (unsigned int i = 0; i < tableId.size(); i++)
	{
		
        	if (*tableId[i] == *id)
            		temp = -1;
		}
	return temp;
}


void TableDesIdentificateurs::afficherTable()
{
	for (unsigned int i = 0; i < tableId.size(); i++)
		cout << i << " | " << *tableId[i] << endl;
}


string TableDesIdentificateurs::getElement(int posId)
{
	return *tableId[posId];
}
