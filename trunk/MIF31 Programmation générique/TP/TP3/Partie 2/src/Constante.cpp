#include "../include/Constante.h"

Constante::Constante(const int& _val) : val(_val){}
Constante::~Constante(){}

int Constante::eval() const{return val;}

Expression* Constante::clone()const{
    return new Constante(val);
}
