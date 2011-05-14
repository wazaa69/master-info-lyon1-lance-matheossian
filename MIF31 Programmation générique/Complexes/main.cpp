#include <iostream>

#include "include/ComplexeCubique.h"
#include "include/Point.h"
#include "include/ICellule.h"

using namespace std;

int main()
{

//    ICellule<2,double,2> *c2 = new ICellule<2,double,2>();

//    cout << c2.estValide() << endl;
//    c2.addBord(c2.getBord(0));

//    ICellule<1, double, 2> c4 = c2.getBord(0);

//    c2.addBord(c3);
//    c2.addBord(c4);
//    c2.addBord(c5);
//    c2.addBord(c6);
//    cout << c2.isValideICellule() << endl;


//    int *coordonnees = new int[2];
//    coordonnees[0] = 1;
//    coordonnees[1] = 2;
//
//
//
//    ComplexeCubique<2,int,2> c;
//    c.creer(2);

    cout << endl;
    ComplexeCubique<3,int,2> c2;
    c2.creer(3);
//    cout << c.estValide() << endl;
//    c.creer(2);
//    c.creer(1);
//    c.creer(0);
//
//    c.creer0Cell(Point<int, 2>(*coordonnees));


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



