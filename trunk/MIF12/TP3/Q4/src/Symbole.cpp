#include <iostream>

#include "Symbole.hpp"


using namespace std;

Symbole::Symbole(){}

Symbole::~Symbole(){}

int Symbole::getID(){return id;}

string* Symbole::getCategorie(){return categorie;}


Type* Symbole::getType(){return type;}


/*
int TableDesSymboles::ajouterIdent(Expression* ex){


	switch(ex->getType()->getStringType())
	{
		case "Boolean":

		  break;
		case "Integer":

		  break;
		case "Real":

		  break;
		case "String":

		  break;
		default:

		  break;
	}


	unsigned int numElem  = getPosId(&id);
	
	 if(numElem == tableId.size())tableId.push_back(new string(id));  //copie

	
	return numElem;
}

*/
