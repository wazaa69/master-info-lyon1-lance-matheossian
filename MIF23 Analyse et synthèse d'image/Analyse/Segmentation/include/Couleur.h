#ifndef COULEUR_H
#define COULEUR_H

#include <opencv/cv.h>
#include <opencv/highgui.h>

#include <stdlib.h>
#include <time.h>

#include <iostream>
#include <vector>

class Couleur
{
    public:

        /** @param si nouvCouleur = true alors les attributs, r,v et b sont initialisés avec une nouvelle couleur, sinon rien est fait  */
        Couleur();
        Couleur(const CvScalar& color); /** @brief color est de forme BGR */
        Couleur(const double& _r, const double& _v, const double& _b);
        virtual ~Couleur();

        const double& operator[](unsigned int i) const;
        bool operator==(const Couleur& c) const;

        void setComposantes(const Couleur& coul);
        void setComposantes(const double& r, const double& v, const double& b);

        const Couleur& getNouvCouleur();
        const bool isSet() const;

        const double& moyenne() const;
        const CvScalar& getCvScalar() const;

    private:

        double r,v,b;

        //pour ne pas avoir deux fois la même couleur
        static std::vector<Couleur> listeCouleurs;

        static void initRand();
        static const double prochainInt();


};

#endif // COULEUR_H
