#include "Variable.hpp"

using namespace std;



 Variable::Variable(Type* _type, int _id){
	categorie = new string("variable "); //copie
    	type = _type;
	id = _id;
	nomSymbole = new string("");
}

  Variable::Variable(TypeUser* _typeUser, int _id){
	categorie = new string("variable "); 
    	type = _typeUser->getType();
	id = _id;
	nomSymbole = new string("");
}


  Variable::Variable(Type* _type, int _id, string _nomSymbole){
	categorie = new string("variable "); //copie
    	type = _type;
	id = _id;
	nomSymbole = new string(_nomSymbole);
}

  Variable::Variable(TypeUser* _typeUser, int _id, string _nomSymbole){
	categorie = new string("variable "); 
    	type = _typeUser->getType();
	id = _id;
	nomSymbole = new string(_nomSymbole);
}


Variable::~Variable(){}

