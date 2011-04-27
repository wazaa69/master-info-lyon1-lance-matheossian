#include "../include/Plus.h"

Plus::Plus(const Expression& _exp1, const Expression& _exp2) : ExpressionBinaire(*_exp1.clone(),*_exp2.clone()){}

Plus::~Plus()
{
    delete exp1;
    delete exp2;
}

int Plus::eval() const{return exp2->eval() + exp1->eval();}

Expression* Plus::clone()const{
    return new Plus(*exp1, *exp2);
}
