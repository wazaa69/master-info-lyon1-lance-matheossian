#ifndef PRODUITLUXE_H
#define PRODUITLUXE_H

#include <iostream>
#include <string>

#include "Produit.h"

class ProduitLuxe : public Produit
{
    public:
        ProduitLuxe(const float&, const std::string&);
        virtual ~ProduitLuxe();

        virtual ProduitLuxe& operator=(const Produit&);

        std::string getNom();

    protected:
        std::string nom;

};

#endif // PRODUITLUXE_H
