#ifndef CONSTANTE_H
#define CONSTANTE_H

#include "Expression.h"

class Constante : public Expression
{
    public:
        Constante(const int& _);
        virtual ~Constante();

        Expression* clone() const;
        int eval() const;

    private:

        int val;


};

#endif // CONSTANTE_H
