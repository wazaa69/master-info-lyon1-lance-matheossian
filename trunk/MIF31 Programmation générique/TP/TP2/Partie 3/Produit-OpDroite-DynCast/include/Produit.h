#ifndef PRODUIT_H
#define PRODUIT_H

#include <iostream>


class Produit
{
    public:
        Produit(const float& _prix);
        virtual ~Produit();

        virtual Produit& operator=(const Produit&);

        float getPrix() const;

    protected:

        float prix;

};

#endif // PRODUIT_H
