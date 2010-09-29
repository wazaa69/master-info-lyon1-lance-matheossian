#include <iostream>

#include "TDI.hpp"


using namespace std;


TableDesIdentificateurs::TableDesIdentificateurs(){}
TableDesIdentificateurs::~TableDesIdentificateurs(){}

int TableDesIdentificateurs::ajouter(string elem){

	std::cout << "Ajout de " << elem << std::endl;
	int numElem  = getId(elem);

	if( < 0){
	tableId.push_back(elem);
	return numElem;}

	if(numElem >= 0)
	return numElem;
}

int TableDesIdentificateurs::getId(string elem)
{
	int temp = -1;
	
	for (unsigned int i = 0; i < tableId.size(); i++)
	{
		if (tableId[i] == elem)
		temp = i;
		
	}
	
	return temp;
}


void TableDesIdentificateurs::afficherTable(int nbID)
{
	cout<<"---"<< nbID << "---" << endl;
	for ( std::vector < std::string >::const_iterator it = tableId.begin () ; it != tableId.end () ; ++ it )
		{
		cout << *it << endl;
		}
}

