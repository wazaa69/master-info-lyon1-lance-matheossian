#ifndef MOINS_H
#define MOINS_H

#include "Expression.h"

class Moins : Expression
{
    public:
        Moins();
        virtual ~Moins();

        Expression* clone() const;
        int eval() const;

    private:

        Expression* exp1;
        Expression* exp2;

};

#endif // MOINS_H
