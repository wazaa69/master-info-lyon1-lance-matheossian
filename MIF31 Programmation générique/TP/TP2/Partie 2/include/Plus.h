#ifndef PLUS_H
#define PLUS_H

#include "ExpressionBinaire.h"

class Plus : public ExpressionBinaire
{
    public:
        Plus(const Expression& _exp1, const Expression& _exp2);
        virtual ~Plus();

        Expression* clone() const;
        int eval() const;

//    private:
//
//        Expression* exp1;
//        Expression* exp2;

};

#endif // PLUS_H
