#include <iostream>

#include "TDI.hpp"


using namespace std;


TableDesIdentificateurs::TableDesIdentificateurs(){}
TableDesIdentificateurs::~TableDesIdentificateurs(){}

void TableDesIdentificateurs::ajouter(string elem){

std::cout << "Ajout de " << elem << std::endl;
tableId.push_back(elem);

}


void TableDesIdentificateurs::afficherTable(int nbID)
{
	cout<<"---"<< nbID << "---" << endl;
	for ( std::vector < std::string >::const_iterator it = tableId.begin () ; it != tableId.end () ; ++ it )
		{
		cout << *it << endl;
		}
}

