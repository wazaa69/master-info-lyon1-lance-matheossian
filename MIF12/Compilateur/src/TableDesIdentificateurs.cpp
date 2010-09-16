#include <iostream>

#include "TableDesIdentificateurs.hpp"

using namespace std;


TableDesIdentificateurs::TableDesIdentificateurs(){}
TableDesIdentificateurs::~TableDesIdentificateurs(){}

void TableDesIdentificateurs::ajouter(char* elem){identificateurs.push_back(elem);}


void TableDesIdentificateurs::afficherTable()
{
	for(unsigned int i=0; i < identificateurs.size(); i++)
		cout << i << "  -->  " << identificateurs[i] << endl;
}
