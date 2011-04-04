#ifndef REGION_H
#define REGION_H

#include <iostream>
#include <vector>

#include "Graine.h"
#include "Couleur.h"


/**
@class Une région -> une couleur.
Chaque région est initialement liée à une graine (la première du tableau de graine).
*/
class Region
{
    public:
        Region(const Graine& graine, const Couleur& couleur);
        virtual ~Region();

        Graine& getGraine();
        const unsigned int& getIndexRegion() const;
        const unsigned int& getIndexRedirection() const;
        const Couleur& getCouleurMoyenne() const;
        const Couleur& getCouleurVisuelle() const;

        /** @brief calcul la moyenne de la couleur actuelle avec la couleur en paramètre */
        void setNouvMoyenne(const Couleur& couleur);
        void setIndexRedirection(const unsigned int& indexRedirection);

        void incTailleRegion();
        void decTailleRegion();
        const int getTailleRegion();

        void incNombreRegion();
        void decNombreRegion();
        const int getNombreRegions();


    private:

        static unsigned int compteurRegions; /** numéro d'indexation de la dernière région créée */
        static unsigned int nombreRegions;
        unsigned int indexRegion; /** numéro d'indexé pour cette région */
        unsigned int indexRedirection;

        Couleur couleurMoyenne; /** la couleur moyenne de la région, pour la segmentation */
        Couleur couleurVisuelle; /** la couleur de la région pour la visualisation sur une image */

        Graine graine; /** pour stocker la graine de départ */
        unsigned int tailleRegion; /** taille de la région en pixels */

};

#endif // REGION_H
