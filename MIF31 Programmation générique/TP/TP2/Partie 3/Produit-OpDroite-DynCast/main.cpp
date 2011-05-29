#include <iostream>

#include"include/Produit.h"
#include"include/ProduitFrais.h"
#include"include/ProduitLuxe.h"
#include"include/ProduitLuxeHorsTaxe.h"

using namespace std;


/**
*  Adaptation à l'opérande de droite avec des dynamic_cast.
*  A l'exécution, le programme va chercher la bonne méthode "operator=".
*/

int main()
{

    Produit* prodFr0  = new ProduitFrais(10, "19/12/10");
    Produit* prodFr1  = new ProduitFrais(11, "19/12/11");

    //on conserve un pointeur de type ProduitFrais (static ou dynamic c'est pareil, de tout façon on connait le type de base à la compilation)
    ProduitFrais* prodFr0_bis = static_cast<ProduitFrais*>(prodFr0);

    //10 "19/12/10"
    cout << prodFr0_bis->getPrix() << endl;
    cout << prodFr0_bis->getPeremption() << endl << endl;

    cout << "Produit 0  = Produit 1 (type dynamic des 2 produits : 'ProduitFrais')" << endl;
    *prodFr0 = *prodFr1;

    cout << "&Produit0 != &Produit1 ? " << (prodFr0 != prodFr1?"true":"false"?"true":"false") << endl << endl;

    //11 "19/12/11"
    cout << prodFr0_bis->getPrix() << endl;
    cout << prodFr0_bis->getPeremption() << endl << endl;


    cout << "--------------------------------------------------------" << endl;

    Produit* prodLx0  = new ProduitLuxeHorsTaxe(20, "Epice", 19);
    Produit* prodLx1  = new ProduitLuxeHorsTaxe(21, "Tapis", 20);

    ProduitLuxeHorsTaxe* prodLx0_bis = static_cast<ProduitLuxeHorsTaxe*>(prodLx0);

    cout << prodLx0_bis->getPrix() << endl;
    cout << prodLx0_bis->getNom() << endl << endl;

    cout << "Produit 0  = Produit 1 (type dynamic des 2 produits : 'ProduitLuxeHorsTaxe')" << endl;
    *prodLx0 = *prodLx1;

    cout << "&Produit0 != &Produit1 ? " << (prodLx0 != prodLx1?"true":"false") << endl << endl;

    cout << prodLx0_bis->getPrix() << endl;
    cout << prodLx0_bis->getNom() << endl << endl;

    return 0;
}
