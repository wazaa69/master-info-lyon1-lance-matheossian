#include "Variable.hpp"

using namespace std;



 Variable::Variable(Type* _type, int _id){
	categorie = new string("variable"); //copie
    	type = _type;
	id = _id;
}



Variable::~Variable(){}

