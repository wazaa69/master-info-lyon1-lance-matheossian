#include <iostream>

#include "include/ComplexeCubique.h"
#include "include/Point.h"
#include "include/ICellule.h"

using namespace std;

int main()
{



    int *coordonnees = new int[2];
    coordonnees[0] = 1;
    coordonnees[1] = 2;

    cout << endl;

//    ICellule<2,int,2> cell1();

    ComplexeCubique<3,int,2> c2;
    c2.creer(2);
//    c2.creer(3);
    Iterator<ICellule<2,int,2>,2> *it2 = c2.ComplexeCubique<2,int,2>::getIterator();
    ICellule<2,int,2> *cell1 = it2->getCellActu();

    c2.creer(2);
    Iterator<ICellule<3,int,2>,3> *it3 = c2.ComplexeCubique<3,int,2>::getIterator();


//    it2getCellActuelle();
//    c2.AjoutICellule((EnsembleCell*)cell1);

//    c2.creer(2);
//    c2.creer(2);

//    c2.creer0Cell( Point<int, 2>(coordonnees));

//    c2.getTab(2);
    cout << "valide "  << c2.estValide() << endl;

//    ComplexeCubique<3,int,2>::Iterator it= c2.begin(2);

//    Point<int, 2> p3();
//    Point<int, 2> *p4 = new Point<int, 2>(p3);


//    Point<int, 2> *p = new Point<int,2>(coordonnees);


//    c2.creer0Cell(Point<int, 2>(*coordonnees));


    cout << "Fin " << endl;
    return 0;
}




    /// TEST POINT.H
//    double *tab = new double[3];
//    char *tab2 = new char[4];
//    Point<double, 3> p1(tab);
//    Point<char, 4> p2(tab2);
//    Point<double, 3> p3( Point<double, 3>);
//
//    Point<double, 3> p4;
//    p4.setCoordonnees(0,1.0);
//    cout << p4.getCoordonnees(1) << endl;



