#include "../include/Region.h"

unsigned int Region::compteurRegions = 0;

Region::Region()
{
}

Region::Region(const Graine& graine, const Couleur& c)
{
    indexRegion = compteurRegions;
    compteurRegions++;

    this->graine.setPtStart(graine.getPtStart());

    listePointsRegion.push_back(graine.getPtStart());

    couleurMoyenne.setComposantes(c);
    couleurVisuelle.setComposantes(Couleur::getNouvCouleur());

}

Region::~Region(){}

const unsigned int& Region::getIndexRegion() const {return indexRegion;}

void Region::setNouvMoyenne(const Couleur& couleur){
    double r = (couleurMoyenne[0] + couleur[0])/2;
    double v = (couleurMoyenne[1] + couleur[1])/2;
    double b = (couleurMoyenne[2] + couleur[2])/2;
    couleurMoyenne.setComposantes(r,v,b);
}

const Couleur& Region::getCouleurMoyenne() const {return couleurMoyenne;}
const Couleur& Region::getCouleurVisuelle() const {return couleurVisuelle;}

unsigned int Region::getCompteurRegions(){return compteurRegions;}

const int Region::getSize(){return listePointsRegion.size();}

std::vector<CvPoint>& Region::getListePointsRegion(){return listePointsRegion;}

void Region::ajouterPointRegion(const CvPoint& p){listePointsRegion.push_back(cvPoint(p.x, p.y));}

