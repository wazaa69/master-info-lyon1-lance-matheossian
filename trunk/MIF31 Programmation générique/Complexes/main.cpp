#include <iostream>

//#include "include/ComplexeCubique.h"
#include "include/Point.h"
#include "include/ICellule.h"

using namespace std;

int main()
{
    cout << "Debut: " << endl;

//    c.afficher();
//    ComplexeCubique<5,double,2> c1;

//    c1.creer();

    double *tab = new double[3];
    Point<double, 2> p(*tab);

    ICellule<0,double,2> c1;
    ICellule<2,double,2> c2;

    cout << "gron " << endl;
    return 0;
}
