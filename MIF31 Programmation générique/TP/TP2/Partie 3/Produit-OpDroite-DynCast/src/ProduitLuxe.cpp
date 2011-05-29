#include "../include/ProduitLuxe.h"

using namespace std;

ProduitLuxe::ProduitLuxe(const float& _prix, const string& _nom) : Produit(_prix), nom(_nom)
{
    //ctor
}

ProduitLuxe::~ProduitLuxe()
{
    //dtor
}


ProduitLuxe& ProduitLuxe::operator=(const Produit& p)
{
    prix = p.getPrix();

    std::cout << "ProduitLuxe::operator= ";

    const ProduitLuxe* tmp;
    if((tmp = dynamic_cast< const ProduitLuxe *>(&p)) != 0)
    {
        nom = tmp->nom;
        std::cout << "-> dynamic_cast Ok !" << std::endl;
    }

    return *this;
}


string ProduitLuxe::getNom(){return nom;}
