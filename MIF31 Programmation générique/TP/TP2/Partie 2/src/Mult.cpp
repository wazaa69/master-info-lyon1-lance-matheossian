#include "../include/Mult.h"

Mult::Mult(const Expression& _exp1, const Expression& _exp2) : ExpressionBinaire(*_exp1.clone(),*_exp2.clone()){}

Mult::~Mult()
{
    delete exp1;
    delete exp2;
}

int Mult::eval() const{return exp2->eval() * exp1->eval();}

Expression* Mult::clone()const{
    return new Mult(*exp1, *exp2);
}
