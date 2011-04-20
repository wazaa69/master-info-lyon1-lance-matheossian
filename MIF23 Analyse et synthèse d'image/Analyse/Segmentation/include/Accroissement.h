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

/**
* @class C'est la classe principale du projet, elle effectue l'acroissement de régions et la fusion, à partir de N graines données
*/
class Accroissement
{
    public:

        /**
        * @brief img_src l'image à segmenter
        * @param _seuil le seuil à respecter pour agréger un point dans une région, ou fusionner deux régions
        * @param _occupationMin le pourcentage d'occupation minimal souhaité sur l'image segmentée (nombre de pixels dans une région*100 / nombre total)
        */
        Accroissement(const IplImage* img_src, const double _seuil = 15, const double _occupationMin = -1);
        virtual ~Accroissement();

        void demarrer(std::vector<Graine>& graines);

        void coloration();

        void afficherInformations();

        const IplImage* getImgSeg() const;
        const IplImage* getImgSrc() const;

        int indexRedirection(const Region& uneRegion);

    private:

        double seuil; /** seuil de différence de couleur */

        const IplImage* img_src;
        IplImage* img_seg;

        std::vector< std::vector<int> > imgIndexGrow; /** tableau de la taille de l'image, stocke à quelle région appartient chaque pixel, initialement à -1 */
        std::vector<Region> listeIndexRegions;

        std::map<int,int> carteRedirections; /** quand une région est agrégé par une autre, on redirige l'index */

        std::queue<CvPoint> listePointsVoisins; /** listes de points -> FIFO */
        std::vector<CvPoint> listePointsVRejetes; /** c'est la listes des points voisins rejetés car ils n'ont pas respectés le seuil.
                                                      On va les remettres dans la pile, dans le bon ordre, donc on utilise un vecteur. */

        unsigned int nbPointsDansRegion; /** nombre de points dans une région */
        unsigned int nbPointsTotal;
        double occupationMin; /** pourcentage d'occupation de l'image souhaité */

        /**
        * @brief Crée autant de région qu'il y a de graine et les sauvegarder dans listeIndexRegions.
        */
        void deposerGraines(std::vector<Graine> graines);

        /**
        * @brief Ajoute la position des graines dans la pile "listePointsVoisins".
        */
        void sePositionnerSurlesGraine(std::vector<Graine> graines);

        /**
        * @brief On récupère et on explore les voisins (8-connexités) des points présents dans la pile "pileDePoints".
        */
        void contaminationPixelsVoisins();
        /**
        * @brief Si les critères d'agrégation sont respectés, la région ou le point sont agrégés dans la région "uneRegion"
        * @param pt le point à tester
        * @param uneRegion la région à laquelle le point pt pourrait appartenir si les testes sont concluants
        */
        void contamination(const CvPoint& pt, Region& uneRegion);

        /**
        * @brief La grande région agrège la petite.
        * L'index de la petite région ne sera plus ajouté dans la matrice d'indexs "imgIndexGrow".
        */
        void changerProprietaireRegion(Region& r_grande, Region& r_petite);




        //ces deux dernières méthodes étaient enc ours de développement

        /**
        * @brief Elle est appelée quand la contatimnation n'a pas respecté le % d'occupation minimal de l'image (il y a donc déjà eu un growing + merging).
        * Elle remet dans la pile les points présents dans "listePointsVRejetes" et qui n'appartiennent pas à une région.
        */
        void contaminationEtendue();

        /**
        * pour plus tard : offrir un choix à l'utilisateur
        * @brief On augmente le seuil selon une loi.
        */
        void augmenteSeuil();



};

#endif // ACROISSEMENT_H
