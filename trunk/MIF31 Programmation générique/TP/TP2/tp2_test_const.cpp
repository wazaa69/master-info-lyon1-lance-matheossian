#include <iostream>

using namespace std;

int main(){
    double d;
    const double r = 5; //on attribut une valeur au r
    const double pi = 3.1416;
    const double *ptr = &pi;
    double *const cpt = &d; //adresse constante, mais contenu à l'adresse modifiable
    double *const ptd = &d;
    const double *ctd = &d; //adresse modifiable mais valeur pointé non modifiable
    const double *ptc = &pi; //ptc n'est pas modifiable, donc la valeur pointée peut être const ou non, c'est pareil
    double *const ptp = const_cast<double *const>(&pi); //ici on précise que la valeur pi est const car on essayait de l'utilisée comme non const
    const double *const ppi = &pi;
    double *const *pptr1 = const_cast<double *const*>(&ptc); //ptc -> pi -> const double. Donc on doit préciser que &ptc est un const

    //test en +
//    **pptr1 = 8; // --> erreur qui fonctionne à part si on le mes dans le main
//    cout << **pptr1 << endl;
//    cout << pi << endl;

    double *const *pptr2 = &ptd; //pas le même soucis que le précédant car on a juste un double

    ptr = new double;
    const_cast<double&>(r) = 1.0; //--> r est const
    //cout << r << endl;
    const_cast<double&>(*ptr) = 2.0; //contenu non modifiable car const
    //cout << *ptr << endl;
    //const_cast<double&>(cpt) = new double; //adresse non modifiable
    *cpt = 3.0;
    ptc = new double;
    //*ptc = 4.0; //c'est const
    //ptd = new double; //@ const
    *ptd = 5.0;
    ctd = new double;
    //*ctd = 6.0;
    //ptp = new double; //@ const
    *ptp = 7.0;
    //ppi = new double;
    //*ppi = 8.0;*/
}

