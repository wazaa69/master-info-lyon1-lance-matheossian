#include <iostream>

#include "Symbole.hpp"


using namespace std;

Symbole::Symbole(){nomSymbole = new string("");}

Symbole::~Symbole(){
	delete categorie;
	delete type;
	delete nomSymbole;
}

int Symbole::getID(){return id;}

string* Symbole::getCategorie(){return categorie;}


Type* Symbole::getType(){return type;}

string* Symbole::getNomSymbole(){return nomSymbole;}

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
