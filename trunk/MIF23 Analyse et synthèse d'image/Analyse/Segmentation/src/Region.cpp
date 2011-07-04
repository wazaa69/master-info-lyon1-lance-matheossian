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

    double r = (couleurMoyenne[0]*tailleRegion + couleur[0])/(tailleRegion+1);
    double v = (couleurMoyenne[1]*tailleRegion + couleur[1])/(tailleRegion+1);
    double b = (couleurMoyenne[2]*tailleRegion + couleur[2])/(tailleRegion+1);

    couleurMoyenne.setComposantes(r,v,b);

    tailleRegion++; //on ajoute le point
}

void Region::setNouvMoyenne(const Couleur& couleur, const unsigned int& tailleRegionAglomeree){

    //on ajoute la région
    const unsigned int nouvTailleRegion = tailleRegion + tailleRegionAglomeree;

    double r = (couleurMoyenne[0]*tailleRegion + couleur[0]*tailleRegionAglomeree)/nouvTailleRegion;
    double v = (couleurMoyenne[1]*tailleRegion + couleur[1]*tailleRegionAglomeree)/nouvTailleRegion;
    double b = (couleurMoyenne[2]*tailleRegion + couleur[2]*tailleRegionAglomeree)/nouvTailleRegion;

    couleurMoyenne.setComposantes(r,v,b);

    tailleRegion = nouvTailleRegion;
}

const Couleur& Region::getCouleurMoyenne() const {return couleurMoyenne;}
const Couleur& Region::getCouleurVisuelle()
{
    if(!couleurVisuelle.isSet()) couleurVisuelle.setComposantes(couleurVisuelle.getNouvCouleur());
    return couleurVisuelle;
}

unsigned int Region::getCompteurRegions(){return compteurRegions;}
void Region::razCompteurRegion(){compteurRegions = 0;}

const Graine& Region::getGraine() const {return graine;}
