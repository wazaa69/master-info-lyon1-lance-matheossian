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

        unsigned int getIndexRegion() const;

        const Couleur& getCouleurMoyenne() const;
        const Couleur& getCouleurVisuelle() const;

        /** @brief calcul la moyenne de la couleur actuelle avec la couleur en paramètre */
        void setNouvMoyenne(const Couleur& couleur);

        static unsigned int getCompteurRegions();

//        void ajouterPointRegion(const CvPoint& p);
//        std::vector<CvPoint>& getListePointsRegion();
//
//        const int getSize();
        unsigned int tailleRegion;

    private:

        static unsigned int compteurRegions; /** numéro d'indexation de la dernière région créée */
        unsigned int indexRegion; /** numéro d'indexé pour cette région */

        Couleur couleurMoyenne; /** la couleur moyenne de la région, pour la segmentation */
        Couleur couleurVisuelle; /** la couleur de la région pour la visualisation sur une image */

        Graine graine; /** pour stocker la graine de départ */
        std::vector<CvPoint> listePointsRegion; /** liste de tous les pixels de la région */


};

#endif // REGION_H
