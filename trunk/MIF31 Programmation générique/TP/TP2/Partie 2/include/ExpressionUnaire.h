#ifndef EXPRESSIONUNAIRE_H
#define EXPRESSIONUNAIRE_H

#include "Expression.h"

class ExpressionUnaire : public Expression
{
    public:
        ExpressionUnaire(const Expression& _exp);
        virtual ~ExpressionUnaire();
        virtual int eval() const =0;
        virtual Expression* clone() const=0;

    protected:
        Expression* exp;

};

#endif // EXPRESSIONUNAIRE_H
