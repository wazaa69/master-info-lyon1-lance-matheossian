#ifndef COULEUR_H
#define COULEUR_H

#include <stdlib.h>
#include <time.h>

#include <iostream>
#include <vector>

class Couleur
{
    public:

        Couleur();
        Couleur(const int& _r, const int& _v, const int& _b);
        virtual ~Couleur();

        const int& operator[](unsigned int i) const;
        bool operator==(const Couleur& c) const;

    private:

        int r,v,b;

        //pour ne pas avoir deux fois la même couleur
        static std::vector<Couleur> listeCouleurs;

        void setComposantes(Couleur& coul);
        void setComposantes(const int& r, const int& v, const int& b);
        static Couleur& getNouvCouleur();

        static void initRand();
        static const int prochainInt();


};

#endif // COULEUR_H
