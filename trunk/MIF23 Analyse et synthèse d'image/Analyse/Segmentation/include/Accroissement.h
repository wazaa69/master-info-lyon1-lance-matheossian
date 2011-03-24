#ifndef ACROISSEMENT_H
#define ACROISSEMENT_H

#include <opencv/cv.h>
#include <opencv/highgui.h>

#include <iostream>
#include <vector>

#include "Region.h"
#include "Graine.h"

class Accroissement
{
    public:
        Accroissement(const char* chemin, const int& _seuil);
        virtual ~Accroissement();

        void demarrer();

    private:

        const int seuil; /** seuil de différence de couleur */

        IplImage* img_src;
        IplImage* img_seg;

        std::vector< std::vector<int> > matriceIndex; /** tableau de la taille de l'image, stocke à quelle région appartient chaque pixel, initialement à -1 */

        std::vector<Region> listeRegions;

        void contaminationVoisins(const Graine& g);
        void contaminationPixel(const CvPoint& pt, const unsigned int& gIndex);
};

#endif // ACROISSEMENT_H
