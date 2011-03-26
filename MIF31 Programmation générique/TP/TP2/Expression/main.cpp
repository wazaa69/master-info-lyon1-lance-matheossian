#include <iostream>

#include "include/Constante.h"
#include "include/Expression.h"
#include "include/MoinsUnaire.h"
#include "include/Moins.h"
#include "include/Plus.h"
#include "include/Multiplication.h"

using namespace std;

int main()
{
    Constante c1(7);
    Constante c2(5);

    Expression* e = c1.clone();
    cout << "Affichage expression e = c1: " << e->eval() << endl;

    MoinsUnaire m_u(c1);
    e = m_u.clone();
    cout << "Affichage -e: " << e->eval() << endl;

    Moins m(c1, c2);
    e = m.clone();
    cout <<  "Affichage e = c1 - c2: " << e->eval() << endl;

    Plus p(c1, c2);
    e = p.clone();
    cout <<  "Affichage e = c1 + c2: " << e->eval() << endl;

    Multiplication mu(c1, c2);
    e = mu.clone();
    cout <<  "Affichage e = c1 * c2: " << e->eval() << endl;

    delete(e);

    return 0;
}
