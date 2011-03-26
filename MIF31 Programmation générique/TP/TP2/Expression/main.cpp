#include <iostream>

#include "include/Constante.h"
#include "include/Expression.h"
#include "include/MoinsUnaire.h"
#include "include/Moins.h"
#include "include/Plus.h"
#include "include/Mult.h"

using namespace std;

int main()
{

    int a=5;

    const Expression & e = Mult(Plus( Constante(a),
                                     MoinsUnaire( Constante(-2))),
                                        Plus( Constante(1),
                                            Constante(3)) );
    std::cout << e.eval() << std::endl;

    return 0;
}
