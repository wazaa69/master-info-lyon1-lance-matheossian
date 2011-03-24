#ifndef REGION_H
#define REGION_H

#include <iostream>
#include <vector>

#include "Graine.h"
#include "Couleur.h"


/**
@class Une région de couleur.
Chaque région est initialement liée à une graine (la première du tableau de graine).
*/
class Region
{
    public:
        Region(const int& _index, Graine& _graine);
        virtual ~Region();

        Graine& getGraine();

    private:

        static std::vector<int> listeIndex; /** stock les index des régions */

        Couleur couleur; /** la couleur de la région */
        std::vector<Graine> listeGraines; /** pour stocker les graines dont les régions étendus ce sont unifiées */

};

#endif // REGION_H
