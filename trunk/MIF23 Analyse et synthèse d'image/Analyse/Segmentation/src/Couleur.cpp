#include "../include/Couleur.h"

std::vector<Couleur> Couleur::listeCouleurs;

Couleur::Couleur(){setComposantes(getNouvCouleur());}
Couleur::Couleur(const int& _r, const int& _v, const int& _b): r(_r), v(_v), b(_b){}
Couleur::~Couleur(){}


void Couleur::setComposantes(Couleur& coul){
    r = coul[0];
    v = coul[1];
    b = coul[2];
}

void Couleur::setComposantes(const int& r, const int& v, const int& b){
    this->r = r;
    this->v = v;
    this->b = b;
}

Couleur& Couleur::getNouvCouleur(){

    Couleur couleur(0,0,0);
    bool existe = true;

    //if(Couleur::listeCouleurs.size() != pow(256,3)){};

    while(existe){

        existe = false;
        couleur.setComposantes(prochainInt(), prochainInt(), prochainInt());

        //std::cout << couleur[0] << " / " << couleur[1] << " / " << couleur[2] << std::endl;

        for(unsigned int i = 0; i < listeCouleurs.size(); i++)
            if(couleur == listeCouleurs[i])
            {
                existe = true;
                break;
            }
    }

    Couleur::listeCouleurs.push_back(couleur);

    return couleur;
}


const int& Couleur::operator[](unsigned int i) const{
    if(i == 0) return r;
    else if (i== 1) return v;
    else return b;
}



bool Couleur::operator==(const Couleur& c) const{

    if(r != c[0] || v != c[1] || b != c[2]) return false;

    return true;
}


void Couleur::initRand(){

    static bool randInitie = false;

    if (!randInitie){
        srand(time(NULL));
        randInitie = true;
    }

}

const int Couleur::prochainInt(){
    initRand();
    return rand()%255;
}
