#ifndef MULT_H
#define MULT_H

#include "ExpressionBinaire.h"

class Mult : public ExpressionBinaire
{
    public:
        Mult(const Expression& _exp1, const Expression& _exp2);
        virtual ~Mult();

        Expression* clone() const;
        int eval() const;

//    private:
//
//        Expression* exp1;
//        Expression* exp2;

};

#endif // MULT_H
