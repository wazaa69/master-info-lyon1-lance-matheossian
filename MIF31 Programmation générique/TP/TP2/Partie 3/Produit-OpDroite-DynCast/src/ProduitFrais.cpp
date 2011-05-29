#include "../include/ProduitFrais.h"

ProduitFrais::ProduitFrais(const float& _prix, const string& _peremption): Produit(_prix), peremption(_peremption)
{
    //ctor
}

ProduitFrais::~ProduitFrais()
{
    //dtor
}

ProduitFrais& ProduitFrais::operator=(const Produit& p)
{
    prix = p.getPrix();

    std::cout << "ProduitFrais::operator= ";

    const ProduitFrais* tmp;
    if((tmp = dynamic_cast< const ProduitFrais *>(&p)) != 0)
    {
        peremption = tmp->peremption;
        std::cout << "-> dynamic_cast Ok !" << std::endl;
    }

    return *this;
}

ProduitFrais& ProduitFrais::operator=(const ProduitFrais& p)
{
    prix = p.getPrix();
    peremption = p.peremption;
}

string ProduitFrais::getPeremption(){return peremption;}
