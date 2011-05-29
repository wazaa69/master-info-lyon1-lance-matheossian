#ifndef PRODUITFRAIS_H
#define PRODUITFRAIS_H

#include <iostream>
#include <string>

#include "Produit.h"

using namespace std;

class ProduitFrais : public Produit
{
    public:
        ProduitFrais(const float& _prix, const string& _peremption);
        virtual ~ProduitFrais();

        ProduitFrais& operator=(const Produit &);
        ProduitFrais& operator=(const ProduitFrais& p);
        std::string getPeremption();

    protected:
        string peremption;

    private:
};

#endif // PRODUITFRAIS_H
