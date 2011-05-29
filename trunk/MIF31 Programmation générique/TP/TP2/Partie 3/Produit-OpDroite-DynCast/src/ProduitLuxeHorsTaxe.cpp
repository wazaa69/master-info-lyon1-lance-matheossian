#include "../include/ProduitLuxeHorsTaxe.h"

using namespace std;

ProduitLuxeHorsTaxe::ProduitLuxeHorsTaxe(const float& _prix, const string& _nom,  const float& _prixHorsTaxe) : ProduitLuxe(_prix, _nom), prixHorsTaxe(_prixHorsTaxe)
{
    //ctor
}

ProduitLuxeHorsTaxe::~ProduitLuxeHorsTaxe()
{
    //dtor
}


ProduitLuxeHorsTaxe& ProduitLuxeHorsTaxe::operator=(const Produit& p)
{
    prix = p.getPrix();

    std::cout << "ProduitLuxeHorsTaxe::operator= ";

    const ProduitLuxeHorsTaxe* tmp;
    if((tmp = dynamic_cast< const ProduitLuxeHorsTaxe *>(&p)) != 0)
    {
        nom = tmp->nom;
        prixHorsTaxe = tmp->prixHorsTaxe;
        std::cout << "-> dynamic_cast Ok !" << std::endl;
    }

    return *this;
}
