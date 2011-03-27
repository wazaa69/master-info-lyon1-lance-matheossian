#ifndef MOINSUNAIRE_H
#define MOINSUNAIRE_H

#include "ExpressionUnaire.h"

class MoinsUnaire : public ExpressionUnaire
{
    public:
        MoinsUnaire(const Expression& _exp);
        virtual ~MoinsUnaire();

        Expression* clone() const;
        int eval() const;

//    private:
//        Expression* exp;

};

#endif // MOINSUNAIRE_H
