#include "../include/ExpressionUnaire.h"

ExpressionUnaire::ExpressionUnaire(const Expression& _exp): exp(_exp.clone()){}

ExpressionUnaire::~ExpressionUnaire()
{
    //dtor
}
