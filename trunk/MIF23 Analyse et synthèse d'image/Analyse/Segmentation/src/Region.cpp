#include "../include/Region.h"

unsigned int Region::compteurRegions = 0;
unsigned int Region::nombreRegions = 0;

Region::Region()
{
}

Region::Region(const Graine& graine, const Couleur& c)
{
    tailleRegion = 1;
    indexRegion = compteurRegions;
    nombreRegions++;
    compteurRegions++;
//    std::cout << "Création de la région n° " << compteurRegions << " | Nombre de régions total à l'instant: " << nombreRegions << std::endl;

    this->graine.setPtStart(graine.getPtStart());

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

void Region::setIndexRedirection(const unsigned int& indexRedirection)
{
    indexRegion = indexRedirection;
}

void Region::incTailleRegion(){tailleRegion++;}
void Region::decTailleRegion(){tailleRegion--;}

void Region::incNombreRegion(){nombreRegions++;}
void Region::decNombreRegion(){nombreRegions--;}

const int Region::getTailleRegion(){return tailleRegion;}
const int Region::getNombreRegions(){return nombreRegions;}

const int Region::getSize(){return listePointsRegion.size();}

std::vector<CvPoint>& Region::getListePointsRegion(){return listePointsRegion;}

void Region::ajouterPointRegion(const CvPoint &p)
{

    listePointsRegion.push_back(p);

}

