#ifndef LECTEUR_H
#define LECTEUR_H

//#include <stdlib.h>
#include <iostream>
#include <fstream>
#include <stdlib.h>

#include <string>
#include <sstream>
#include <vector>

#include "ComplexeCubique.h"
#include "Point.h"

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

    private:

        unsigned int dimComplexe;
        unsigned int dimPoint;
        unsigned int dimMaxCellule;
        vector<int> nbICellules;

        vector<Point<int,T_DIMENSION>* > p;
        vector<ICellule<0,int,T_DIMENSION>* > c0;
        vector<ICellule<1,int,T_DIMENSION>* > c1;
        vector<ICellule<2,int,T_DIMENSION>* > c2;
        vector<ICellule<3,int,T_DIMENSION>* > c3;
};

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void Lecteur<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>::chargerDonnees(string cheminFichier, ComplexeCubique<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION> &_c)
{
    int numLigne = 1;
    cout << "Lecture du fichier de flot: " << endl << endl;
    ifstream f;
    char caractere = 'd';
    f.open(cheminFichier.c_str());
    if(f){ string contenu;

        char split_char = ' ';
        string ligne;

        // récupération des différentes dimensions
        while(numLigne < 3)
        {
            getline(f, ligne);
            istringstream split(ligne);
            std::vector<std::string> tokens;
            for (std::string each; std::getline(split, each, split_char); tokens.push_back(each));

            if(numLigne == 1)
            {
                dimComplexe = atoi(tokens[0].c_str());
                dimPoint = atoi(tokens[1].c_str());
            }
            if(numLigne == 2)
            {
                for(unsigned int i = 0; i < tokens.size(); i++) nbICellules.push_back(atoi(tokens[i].c_str()));
                dimMaxCellule = tokens.size();
            }

            numLigne++;
        }

        int dimActu = 0;
        int nbTotalLigne = 0;
        for(int i = 0; i < dimMaxCellule; i++ ){nbTotalLigne+= nbICellules[i];}
        cout << nbTotalLigne << endl;

        while(getline(f, ligne))
        {
            istringstream split(ligne);
            std::vector<std::string> tokens;
            for (std::string each; std::getline(split, each, split_char); tokens.push_back(each));

//            if(numLigne < nbICellules[nbICellules])
//            {
//
//
//
//            }
//            else
//            {
//                dimActu++;
//            }

            numLigne++;
        }

    }
}


#endif // LECTEUR_H
