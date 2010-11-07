#include "Temporaire.hpp"

using namespace std;

Temporaire::Temporaire(int _id, Expression* _ex)
{
	categorie = new string("temporaire"); 
	id = _id;
	ex = _ex;
}

Expression* Temporaire::getExpression(){ return ex;}

Temporaire::~Temporaire(){}

