#include "../include/Moins.h"

Moins::Moins(const Expression& _exp1, const Expression& _exp2) : ExpressionBinaire(*_exp1.clone(),*_exp2.clone()){}

Moins::~Moins()
{
    delete exp1;
    delete exp2;
}

int Moins::eval() const{return exp2->eval() - exp1->eval();}

Expression* Moins::clone()const{
    return new Moins(*exp1, *exp2);
}
