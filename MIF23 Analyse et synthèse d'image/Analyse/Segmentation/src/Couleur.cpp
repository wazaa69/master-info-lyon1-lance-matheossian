#include "../include/Couleur.h"

std::vector<Couleur> Couleur::listeCouleurs;

Couleur::Couleur():r(-1), v(-1), b(-1){}
Couleur::Couleur(const CvScalar& color): r(color.val[2]), v(color.val[1]), b(color.val[0]){}
Couleur::Couleur(const double& _r, const double& _v, const double& _b): r(_r), v(_v), b(_b){}
Couleur::~Couleur(){}


void Couleur::setComposantes(const Couleur& coul){
    r = coul[0];
    v = coul[1];
    b = coul[2];
}

void Couleur::setComposantes(const double& r, const double& v, const double& b){
    this->r = r;
    this->v = v;
    this->b = b;
}

const Couleur& Couleur::getNouvCouleur(){

    Couleur couleur(0,0,0);
    bool existe = true;

    couleur.setComposantes(prochainInt(), prochainInt(), prochainInt());

    //une nouvelle couleur est créée. ---> boucle trop lourde !!
//    while(existe){
//
//        existe = false;
//        couleur.setComposantes(prochainInt(), prochainInt(), prochainInt());
//
//        //std::cout << couleur[0] << " / " << couleur[1] << " / " << couleur[2] << std::endl;
//
//        for(unsigned int i = 0; i < listeCouleurs.size(); i++)
//            if(couleur == listeCouleurs[i])
//            {
//                existe = true;
//                break;
//            }
//    }

    Couleur::listeCouleurs.push_back(couleur);

    return couleur;
}

const double& Couleur::moyenne() const {return (r+v+b)/3;}

const CvScalar& Couleur::getCvScalar() const {return cvScalar(b,v,r);}

const double& Couleur::operator[](unsigned int i) const{
    if(i == 0) return r;
    else if (i== 1) return v;
    else return b;
}


bool Couleur::operator==(const Couleur& c) const{

    if(r != c[0] || v != c[1] || b != c[2]) return false;

    return true;
}


const bool Couleur::isSet() const
{
    if(r != -1 && v != -1 && b != -1) return true;
    return false;
}

void Couleur::initRand(){

    static bool randInitie = false;

    if (!randInitie){
        srand(time(NULL));
        randInitie = true;
    }

}

const double Couleur::prochainInt(){
    initRand();
    return rand()%256; //0 à 255
}
