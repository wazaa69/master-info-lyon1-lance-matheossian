#ifndef PRODUITFRAIS_H
#define PRODUITFRAIS_H

#include "Produit.h"


class ProduitFrais : public Produit
{
    public:
        ProduitFrais();
        virtual ~ProduitFrais();

        ProduitFrais & operator=(const Produit &p);
    protected:
    private:
};

#endif // PRODUITFRAIS_H
