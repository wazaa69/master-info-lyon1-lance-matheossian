#include <iostream>

#include "include/ComplexeCubique.h"
#include "include/Point.h"
//#include "include/ICellule.h"

using namespace std;

int main()
{
    cout << "Debut: " << endl;

    ComplexeCubique<2,double,2> c;
    c.creer(2);
    c.creer(1);
//


//    ICellule<0,double,2> c1;


//    ICellule<2,double,2> c5( ICellule<2,double,2>);



    cout << "fin " << endl;
    return 0;
}

//    double *tab = new double[3];
//    char *tab2 = new char[4];
//    Point<double, 2> p1(*tab);
//    Point<char, 2> p2(*tab2);


//    ICellule<2,double,2> c2;
//    ICellule<1,double,2> c3;
//    ICellule<1,double,2> c4;
//    ICellule<1,double,2> c5;
//    ICellule<1,double,2> c6;
//    c2.addBord(c3);
//    c2.addBord(c4);
//    c2.addBord(c5);
//    c2.addBord(c6);
//    cout << c2.isValideICellule() << endl;
