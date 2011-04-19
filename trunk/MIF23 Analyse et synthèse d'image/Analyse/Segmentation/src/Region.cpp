#include "../include/Region.h"

unsigned int Region::compteurRegions = 0;

Region::Region(){}

Region::Region(const Graine& graine, const Couleur& c)
{

    tailleRegion=1;
    indexRegion = compteurRegions;
    compteurRegions++;

    this->graine.setPtStart(graine.getPtStart());

    couleurMoyenne.setComposantes(c);
}

Region::~Region(){}

unsigned int Region::getIndexRegion() const {return indexRegion;}

unsigned int Region::getTailleRegion() const {return tailleRegion;}
void Region::setTailleRegion(unsigned int valeur){tailleRegion = valeur;}


void Region::setNouvMoyenne(const Couleur& couleur){
    double r = (couleurMoyenne[0] + couleur[0])/2;
    double v = (couleurMoyenne[1] + couleur[1])/2;
    double b = (couleurMoyenne[2] + couleur[2])/2;
    couleurMoyenne.setComposantes(r,v,b);
}

const Couleur& Region::getCouleurMoyenne() const {return couleurMoyenne;}
const Couleur& Region::getCouleurVisuelle()
{
    if(!couleurVisuelle.isSet()) couleurVisuelle.setComposantes(couleurVisuelle.getNouvCouleur());
    return couleurVisuelle;
}

unsigned int Region::getCompteurRegions(){return compteurRegions;}

const Graine& Region::getGraine() const {return graine;}
