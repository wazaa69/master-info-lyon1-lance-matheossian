#include "../include/Produit.h"

Produit::Produit()
{
    //ctor
}

Produit::~Produit()
{
    //dtor
}
Produit & Produit::operator=(const Produit & p)
{
    prix = p.prix;
    return *this;
}
