#include "../include/MoinsUnaire.h"

MoinsUnaire::MoinsUnaire(const Expression& _exp): ExpressionUnaire(*_exp.clone()){}

MoinsUnaire::~MoinsUnaire(){delete exp;}

int MoinsUnaire::eval() const{return - exp->eval();}

Expression* MoinsUnaire::clone()const{
    return new MoinsUnaire(*exp);
}
