#include "../include/ExpressionBinaire.h"

ExpressionBinaire::ExpressionBinaire(const Expression& _exp1, const Expression& _exp2) : exp1(_exp1.clone()), exp2(_exp2.clone()){}

ExpressionBinaire::~ExpressionBinaire()
{
    //dtor
}
