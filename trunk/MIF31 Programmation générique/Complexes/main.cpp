#include <iostream>

#include "include/ComplexeCubique.h"
#include "include/Point.h"
#include "include/ICellule.h"

using namespace std;

int main()
{

    int *coordP0 = new int[2]; coordP0[0] = 0; coordP0[1] = 0; // coordonnées point 0
    int *coordP1 = new int[2]; coordP1[0] = 0; coordP1[1] = 1; // coordonnées point 1
    int *coordP2 = new int[2]; coordP2[0] = 1; coordP2[1] = 0; // coordonnées point 2
    int *coordP3 = new int[2]; coordP3[0] = 1; coordP3[1] = 1; // coordonnées point 3
    int *coordP4 = new int[2]; coordP4[0] = 2; coordP4[1] = 1; // coordonnées point 4
    int *coordP5 = new int[2]; coordP5[0] = 2; coordP5[1] = 2; // coordonnées point 5
    int *coordP6 = new int[2]; coordP6[0] = 3; coordP6[1] = 2; // coordonnées point 6
    int *coordP7 = new int[2]; coordP7[0] = 3; coordP7[1] = 1; // coordonnées point 7
    int *coordP8 = new int[2]; coordP8[0] = 4; coordP8[1] = 1; // coordonnées point 8
    int *coordP9 = new int[2]; coordP9[0] = 4; coordP9[1] = 2; // coordonnées point 9

    Point<int,2> P0(coordP0); Point<int,2> P1(coordP1); Point<int,2> P2(coordP2); Point<int,2> P3(coordP3); Point<int,2> P4(coordP4);
    Point<int,2> P5(coordP5); Point<int,2> P6(coordP6); Point<int,2> P7(coordP7); Point<int,2> P8(coordP8); Point<int,2> P9(coordP9);

    ICellule<0,int,2> *I0_0 = new ICellule<0,int,2>(P0);
    ICellule<0,int,2> *I0_1 = new ICellule<0,int,2>(P1);
    ICellule<0,int,2> *I0_2 = new ICellule<0,int,2>(P2);
    ICellule<0,int,2> *I0_3 = new ICellule<0,int,2>(P3);
    ICellule<0,int,2> *I0_4 = new ICellule<0,int,2>(P4);
    ICellule<0,int,2> *I0_5 = new ICellule<0,int,2>(P5);
    ICellule<0,int,2> *I0_6 = new ICellule<0,int,2>(P6);
    ICellule<0,int,2> *I0_7 = new ICellule<0,int,2>(P7);
    ICellule<0,int,2> *I0_8 = new ICellule<0,int,2>(P8);
    ICellule<0,int,2> *I0_9 = new ICellule<0,int,2>(P9);

    std::vector<ICellule<0,int,2> *>v1_0,v1_1,v1_2,v1_3,v1_4,v1_5,v1_6,v1_7,v1_8,v1_9;
    v1_0.push_back(I0_0); v1_0.push_back(I0_1); v1_1.push_back(I0_2); v1_1.push_back(I0_3);
    v1_2.push_back(I0_1); v1_2.push_back(I0_3); v1_3.push_back(I0_3); v1_3.push_back(I0_4);
    v1_4.push_back(I0_4); v1_4.push_back(I0_7); v1_5.push_back(I0_4); v1_5.push_back(I0_5);
    v1_6.push_back(I0_5); v1_6.push_back(I0_6); v1_7.push_back(I0_7); v1_7.push_back(I0_8);
    v1_8.push_back(I0_6); v1_8.push_back(I0_9); v1_9.push_back(I0_7); v1_9.push_back(I0_6);

    ICellule<1,int,2> *I1_0 = new ICellule<1,int,2>(v1_0); ICellule<1,int,2> *I1_1 = new ICellule<1,int,2>(v1_1);
    ICellule<1,int,2> *I1_2 = new ICellule<1,int,2>(v1_2); ICellule<1,int,2> *I1_3 = new ICellule<1,int,2>(v1_3);
    ICellule<1,int,2> *I1_4 = new ICellule<1,int,2>(v1_4); ICellule<1,int,2> *I1_5 = new ICellule<1,int,2>(v1_5);
    ICellule<1,int,2> *I1_6 = new ICellule<1,int,2>(v1_6); ICellule<1,int,2> *I1_7 = new ICellule<1,int,2>(v1_7);
    ICellule<1,int,2> *I1_8 = new ICellule<1,int,2>(v1_8); ICellule<1,int,2> *I1_9 = new ICellule<1,int,2>(v1_9);

    std::vector<ICellule<1,int,2> *>v2_0;
    v2_0.push_back(I1_4); v2_0.push_back(I1_9); v2_0.push_back(I1_5); v2_0.push_back(I1_6);

    ICellule<2,int,2> *I2_0 = new ICellule<2,int,2>(v2_0);

    ComplexeCubique<2,int,2> complexe;

    complexe.ComplexeCubique<2,int,2>::ajout(*I2_0); // ajout d'une 2-cellule dans le complexe

    complexe.ComplexeCubique<1,int,2>::ajout(*I1_0); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_1); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_2); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_3); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_4); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_5); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_6); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_7); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_8); // ajout d'une 1-cellule dans le complexe
    complexe.ComplexeCubique<1,int,2>::ajout(*I1_9); // ajout d'une 1-cellule dans le complexe

    complexe.ComplexeCubique<0,int,2>::ajout(*I0_0); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_1); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_2); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_3); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_4); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_5); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_6); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_7); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_8); // ajout d'une 0-cellule dans le complexe
    complexe.ComplexeCubique<0,int,2>::ajout(*I0_9); // ajout d'une 0-cellule dans le complexe

    Iterator<ICellule<0,int,2>,0> *it0 = complexe.ComplexeCubique<0,int,2>::getIterator();
    Iterator<ICellule<1,int,2>,1> *it1 = complexe.ComplexeCubique<1,int,2>::getIterator();
    Iterator<ICellule<2,int,2>,2> *it2 = complexe.ComplexeCubique<2,int,2>::getIterator();

    cout << "Il y a " << it0->getTaille() << " cellules de dimension 0 dans le complexe. " << endl;
    cout << "Il y a " << it1->getTaille() << " cellules de dimension 1 dans le complexe. " << endl;
    cout << "Il y a " << it2->getTaille() << " cellules de dimension 2 dans le complexe. " << endl;

    cout << endl;



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



