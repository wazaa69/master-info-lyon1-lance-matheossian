#ifndef ACROISSEMENT_H
#define ACROISSEMENT_H

#include <opencv/cv.h>
#include <opencv/highgui.h>

#include <iostream>
#include <vector>
#include <queue>
#include <map>

#include <time.h>

#include "Region.h"
#include "Graine.h"

class Accroissement
{
    public:
        Accroissement(const IplImage* img_src, const double& _seuil);
        virtual ~Accroissement();

        void dispositionAutomatique(std::vector<Graine>* graines) const;
        void dispositionAleatoire(std::vector<Graine>* graines, const unsigned int nombre) const;
        void afficherInformations();

        void demarrer(std::vector<Graine>& graines);

        void coloration();

        const IplImage* getImgSeg() const;
        const IplImage* getImgSrc() const;

        int indexRedirection(const Region& uneRegion);

    private:

        const double seuil; /** seuil de différence de couleur */

        const IplImage* img_src;
        IplImage* img_seg;

        std::vector< std::vector<int> > imgIndexGrow; /** tableau de la taille de l'image, stocke à quelle région appartient chaque pixel, initialement à -1 */
        std::vector<Region> listeIndexRegions;

        std::map<int,int> carteRedirections;

        std::queue<CvPoint> listePointsVoisins; //FIFO

        void deposerGraines(std::vector<Graine> graines);
        void sePositionnerSurlesGraine(std::vector<Graine> graines);

        //grow
        void contaminationPixelsVoisins();
        void contaminationPixel(const CvPoint& pt, Region& uneRegion);

        void changerProprietaireRegion(Region& r_grande, Region& r_petite, IplImage* img);

};

#endif // ACROISSEMENT_H
