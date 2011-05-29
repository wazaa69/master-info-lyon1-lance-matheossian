#ifndef PRODUITLUXEHORSTAXE_H
#define PRODUITLUXEHORSTAXE_H

#include <iostream>
#include <string>

#include "ProduitLuxe.h"


class ProduitLuxeHorsTaxe : public ProduitLuxe
{
    public:
        ProduitLuxeHorsTaxe(const float&, const std::string&, const float&);
        virtual ~ProduitLuxeHorsTaxe();

        ProduitLuxeHorsTaxe& operator=(const Produit& p);

    protected:
        float prixHorsTaxe;

};

#endif // PRODUITLUXEHORSTAXE_H
