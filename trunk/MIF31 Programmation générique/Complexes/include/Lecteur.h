#ifndef LECTEUR_H
#define LECTEUR_H

//#include <stdlib.h>
#include <iostream>
#include <fstream>


#include <string>
#include <vector>

#include "ComplexeCubique.h"
using namespace std;

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
class Lecteur
{
    public:
        Lecteur(){}
        virtual ~Lecteur(){}

        /** @brief Chargement des données d’un complexe cubique à partir d’un fichier */
        void chargerDonnees(std::string cheminFichier, ComplexeCubique<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION> &_c);

        /** @brief Sauvegarder les données d'un complexe cubique dans un fichier */
        static void sauvegarderDonnees(std::string cheminFichier);

};

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void Lecteur<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>::chargerDonnees(string cheminFichier, ComplexeCubique<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION> &_c)
{
//    ifstream f;
//    char caractere = 'd';
//    f.open(cheminFichier.c_str());
//    if(f){ string contenu;
//
//        while (caractere != ' ' && caractere != '\n')
//        {
//            f.get(caractere);
//        }
//
//    }
}


#endif // LECTEUR_H
