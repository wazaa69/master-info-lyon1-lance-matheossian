#include "../include/ProduitFrais.h"

ProduitFrais::ProduitFrais()
{
    //ctor
}

ProduitFrais::~ProduitFrais()
{
    //dtor
}

ProduitFrais & ProduitFrais::operator=(const Produit &p)
{
    ProduitFrais *temp;

    if((temp=dynamic_cast< const ProduitFrais *>(&p))!=0)
    {
        prix = p.prix;
    }
    else
    {
        prix = 0;
    }
    return *this;
}
