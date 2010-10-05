#include <string>

#include "Type.hpp"

using namespace std;

Type::Type(string _type){
    type = new string(_type); //copie
}

Type::~Type(){}

string* Type::getStringType(){return type;}
