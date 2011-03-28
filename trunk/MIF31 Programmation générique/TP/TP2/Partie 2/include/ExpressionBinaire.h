#ifndef EXPRESSIONBINAIRE_H
#define EXPRESSIONBINAIRE_H

#include "Expression.h"


class ExpressionBinaire : public Expression
{
    public:
        ExpressionBinaire(const Expression& _exp1, const Expression& _exp2);
        virtual ~ExpressionBinaire();
        virtual int eval() const =0;
        virtual Expression* clone() const=0;

    protected:
        Expression* exp1;
        Expression* exp2;

};

#endif // EXPRESSIONBINAIRE_H
