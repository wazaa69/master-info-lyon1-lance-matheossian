#include <iostream>

#include "Symbole.hpp"

using namespace std;


Symbole::Symbole(string _categorie, Type* _type){
    categorie = new string(_categorie); //copie
    type = _type;
}

Symbole::~Symbole(){}


string* Symbole::getCategorie(){return categorie;}


Type* Symbole::getType(){return type;}
