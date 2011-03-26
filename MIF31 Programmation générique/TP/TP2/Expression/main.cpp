#include <iostream>

#include "include/Constante.h"
#include "include/Expression.h"
#include "include/MoinsUnaire.h"
#include "include/Moins.h"

using namespace std;

int main()
{
    Constante c1(7);
    Constante c2(5);

    Expression* e = c1.clone();
    cout << "Affichage expression e = c1: " << e->eval() << endl;
    delete(e);

    MoinsUnaire m_u(c1);
    e = m_u.clone();
    cout << "Affichage -e: " << e->eval() << endl;
    delete(e);

    Moins m(c1, c2);
    e = m.clone();
    cout <<  "Affichage e = c1 - c2: " << e->eval() << endl;
    delete(e);

    return 0;
}
