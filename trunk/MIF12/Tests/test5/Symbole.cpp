#include <iostream>

#include "Symbole.hpp"

using namespace std;


Symbole::Symbole(string _categorie){
    categorie = new string(_categorie); //copie

}


Symbole::Symbole(string _categorie, Type* _type){
    categorie = new string(_categorie); //copie
    type = _type;
}


 Symbole::Symbole(string _categorie, Type* _type, int _id){
	categorie = new string(_categorie); //copie
    	type = _type;
	id = _id;
}

Symbole::~Symbole(){}


string* Symbole::getCategorie(){return categorie;}


Type* Symbole::getType(){return type;}

int Symbole::getID(){return id;}
