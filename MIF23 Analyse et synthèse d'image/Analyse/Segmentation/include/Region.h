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

        Region();
        Region(const Graine& graine, const Couleur& couleur);
        virtual ~Region();

        Graine& getGraine();

        /**
        * @return retourne l'identifiant de la région
        */
        unsigned int getIndexRegion() const;

        /**
        * @return retourne le nombre de points contenus dans la région
        */
        unsigned int getTailleRegion() const;
        void setTailleRegion(unsigned int valeur);
        const Couleur& getCouleurMoyenne() const;
        const Couleur& getCouleurVisuelle();

        /** @brief calcul la moyenne de la couleur actuelle avec la couleur passée en paramètre */
        void setNouvMoyenne(const Couleur& couleur);

        /**
        * @return retourne le nombre total de région
        */
        static unsigned int getCompteurRegions();
        static void razCompteurRegion();

        /**
        * @return retourne la graine de la région
        */
        const Graine& getGraine() const;

    private:

        static unsigned int compteurRegions; /** numéro d'indexation de la dernière région créée */
        unsigned int indexRegion; /** numéro d'index pour cette région */

        Couleur couleurMoyenne; /** la couleur moyenne de la région, pour la segmentation */
        Couleur couleurVisuelle; /** la couleur de la région pour la visualisation sur une image */

        Graine graine; /** la graine de départ (c'est à partir de cette graine que va croître la région) */
        unsigned int tailleRegion; /** nombre de points qui composent la région */


};

#endif // REGION_H
