#include "Moins.h"

Moins::Moins(const Expression& _exp1, const Expression& _exp2) expr1(_exp1.clone()), expr2(_expr2.clone()){}

Moins::~Moins()
{
    delete expr1;
    delete expr2;
}

int Moins::eval() const{return exp2->eval() - exp1->eval();}

Expression* Moins::clone()const{
    return new Moins(*exp1, *expr2);
}
