#include <iostream>

#include "include/Constante.h"
#include "include/Expression.h"
#include "include/MoinsUnaire.h"

using namespace std;

int main()
{
    Constante c1(5);
    Constante c2(7);

    Expression* e = c1.clone();
    cout << e->eval() << endl;
    delete(e);

    MoinsUnaire u(c1);
    e = u.clone();
    cout << e->eval() << endl;
    delete(e);

    Moins u(c1, c2);
    e = u.clone();
    cout << e->eval() << endl;
    delete(e);

    return 0;
}
