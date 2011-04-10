#ifndef ACROISSEMENT_H
#define ACROISSEMENT_H

#include <opencv/cv.h>
#include <opencv/highgui.h>

#include <iostream>
#include <vector>
#include <queue>
#include <map>

#include "Region.h"
#include "Graine.h"

class Accroissement
{
    public:
        Accroissement(const char* chemin, const double& _seuil);
        virtual ~Accroissement();

        void demarrer(std::vector<Graine> graines);
        void dispositionAutomatique(std::vector<Graine> *graines);
        void afficherInformations();

        const IplImage* getImgSeg() const;
        const IplImage* getImgSrc() const;

    private:

        const double seuil; /** seuil de différence de couleur */

        IplImage* img_src;
        IplImage* img_seg;

        std::vector< std::vector<int> > imgIndexGrow; /** tableau de la taille de l'image, stocke à quelle région appartient chaque pixel, initialement à -1 */
        std::vector< std::vector<bool> > imgIndexMerge; /** similaire au tableau précédant mais pour la partie merging, si false = non traité, false sinon */
        std::vector<Region> listeIndexRegions;
        std::map<int,int> mapDeRedirections;

        std::queue<CvPoint> listePointsVoisins; //FIFO

        void deposerGraines(std::vector<Graine> graines);
        void sePositionnerSurlesGraine(std::vector<Graine> graines);

        //grow
        void contaminationPixelsVoisins();
        void contaminationPixel(const CvPoint& pt, Region& region);

        void changerProprietaireRegion(Region& r_grande, Region& r_petite, IplImage* img);

};

#endif // ACROISSEMENT_H
