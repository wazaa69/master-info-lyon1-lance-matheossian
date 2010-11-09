#include "Temporaire.hpp"



using namespace std;

Temporaire::Temporaire(int _id, Expression* _ex)
{
	categorie = new string("temporaire"); 
	id = _id;
	ex = _ex;
}

Temporaire::Temporaire(int _id, string _nomTemporaire, Type* _type)
{
	categorie = new string("temporaire");
	id = _id;
	nomTemporaire = new string(_nomTemporaire);
	type = _type;

}

Expression* Temporaire::getExpression(){ return ex;}

Temporaire::~Temporaire(){}

