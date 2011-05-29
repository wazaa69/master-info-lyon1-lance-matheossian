#include "../include/Produit.h"

Produit::Produit(const float& _prix) : prix(_prix)
{
    //ctor
}

Produit::~Produit()
{
    //dtor
}
Produit& Produit::operator=(const Produit& p)
{
    std::cout << "ProduitFrais::operator=" << std::endl;
    prix = p.prix;
    return *this;
}

float Produit::getPrix() const{return prix;}
