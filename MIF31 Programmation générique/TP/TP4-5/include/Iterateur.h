#ifndef ITERATEUR_H
#define ITERATEUR_H

#include "../include/Tableau.h"

class Tableau<>;

template <class T, int agrandissement>
class TabIterator
{

    public:
        TabIterator();
        TabIterator(int _position);
        virtual ~TabIterator();
    protected:
    private:

        int position;
        Tableau* tab;
};

#endif // ITERATEUR_H
