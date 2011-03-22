#ifndef MOINSUNAIRE_H
#define MOINSUNAIRE_H

#include "Expression.h"

class MoinsUnaire : public Expression
{
    public:
        MoinsUnaire(const Expression& _exp);
        virtual ~MoinsUnaire();

        Expression* clone() const;
        int eval() const;

    private:

        Expression* exp;

};

#endif // MOINSUNAIRE_H
