#ifndef LECTEUR_H
#define LECTEUR_H

#include <iostream>
#include <fstream>
#include <stdlib.h>

#include <string>
#include <sstream>
#include <vector>

#include "ComplexeCubique.h"
#include "Point.h"
#include "ICellule.h"


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
        void sauvegarderDonnees(string cheminFichier, const ComplexeCubique<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>&  _c);

    private:

        unsigned int dimComplexe;
        unsigned int dimPoint;
        unsigned int dimMaxCellule;
        vector<int> nbICellules;

        vector<ICellule<0,T_TYPE,T_DIMENSION>* > c0;
        vector<ICellule<1,T_TYPE,T_DIMENSION>* > c1;
        vector<ICellule<2,T_TYPE,T_DIMENSION>* > c2;
        vector<ICellule<3,T_TYPE,T_DIMENSION>* > c3;
};

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void Lecteur<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>::chargerDonnees(string cheminFichier, ComplexeCubique<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION> &_c)
{
    int numLigne = 1;
    cout << endl << "Lecture du fichier de flot: " << endl << endl;
    ifstream f;
    char caractere = 'd';
    f.open(cheminFichier.c_str());
    bool s1 = 0,s2 = 0, s3 = 0, s4 = 0;

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
                dimMaxCellule = tokens.size()-1;
            }

            numLigne++;
        }

        getline(f, ligne);// saut de ligne

        int nbTotalLigne = 0;
        for(int i = 0; i < dimMaxCellule; i++ ){nbTotalLigne+= nbICellules[i];}

        while(getline(f, ligne))
        {
            istringstream split(ligne);
            std::vector<std::string> tokens;
            for (std::string each; std::getline(split, each, split_char); tokens.push_back(each));

            if(numLigne <= nbICellules[0] + 2)
            {
                T_TYPE *coord = new T_TYPE[T_DIMENSION];

                for(unsigned int j = 0; j < T_DIMENSION; j++)
                {
                    coord[j]  = atoi(tokens[j].c_str());
                }

                Point<T_TYPE,T_DIMENSION> P(coord);
                ICellule<0,T_TYPE,T_DIMENSION> *I0 = new ICellule<0,T_TYPE,T_DIMENSION>(P);
                c0.push_back(I0);

                _c.ComplexeCubique<0,T_TYPE,T_DIMENSION>::ajout(*I0);

            }
            else if (dimMaxCellule > 0)
            {


                if(numLigne <= nbICellules[0] + nbICellules[1] + 2)
                {
                    if(s1 == false){
                        getline(f, ligne); s1 = true;
                                tokens.clear();
                                stringstream split(ligne);
                                for (std::string each; std::getline(split, each, split_char); tokens.push_back(each));
                    }   // saut de ligne

                    std::vector<ICellule<0,T_TYPE, T_DIMENSION> *>v0;

                    v0.push_back(c0.at(atoi(tokens[0].c_str())));
                    v0.push_back(c0.at(atoi(tokens[1].c_str())));

                    ICellule<1,T_TYPE, T_DIMENSION> *I1 = new ICellule<1,T_TYPE,T_DIMENSION>(v0);
                    c1.push_back(I1);

                    _c.ComplexeCubique<1,T_TYPE,T_DIMENSION>::ajout(*I1);

                }
                else if (dimMaxCellule > 1)
                {


                    if(numLigne <= nbICellules[0] + nbICellules[1] + nbICellules[2] + 2)
                    {

                        if(s2 == false){getline(f, ligne); s2 = true;
                                    tokens.clear();
                                    stringstream split(ligne);
                                    for (std::string each; std::getline(split, each, split_char); tokens.push_back(each));
                        }   // saut de ligne

                        std::vector<ICellule<1,T_TYPE, T_DIMENSION> *>v1;

                        for(unsigned int j = 0; j < tokens.size(); j++)
                        {
                            v1.push_back(c1.at(atoi(tokens[j].c_str())));
                        }

                        ICellule<2,T_TYPE, T_DIMENSION> *I2 = new ICellule<2,T_TYPE,T_DIMENSION>(v1);
                        c2.push_back(I2);

                        _c.ComplexeCubique<2,T_TYPE,T_DIMENSION>::ajout(*I2);


                    }
                    else if (dimMaxCellule > 2)
                    {

                        if(numLigne <= nbICellules[0] + nbICellules[1] + nbICellules[2] + nbICellules[3] + 2)
                        {
                            if(s3 == false){getline(f, ligne); s3 = true;
                                        tokens.clear();
                                        stringstream split(ligne);
                                        for (std::string each; std::getline(split, each, split_char); tokens.push_back(each));
                            }   // saut de ligne

                            cout <<atoi(tokens[0].c_str()) << endl;
                            std::vector<ICellule<2,T_TYPE, T_DIMENSION> *>v2;

                            for(unsigned int j = 0; j < tokens.size(); j++)
                            {
                                v2.push_back(c2.at(atoi(tokens[j].c_str())));
                            }

                            ICellule<3,T_TYPE, T_DIMENSION> *I3 = new ICellule<3,T_TYPE,T_DIMENSION>(v2);
                            c3.push_back(I3);

                            _c.ComplexeCubique<3,T_TYPE,T_DIMENSION>::ajout(*I3);
                        }
                        else if (dimMaxCellule > 3)
                        {


                        }
                    }
                }
            }
            numLigne++;
        }

    }
    cout << endl << "Verification de la validite: ";
    assert(_c.estValide());
    cout << "Le complexe lu est valide." << endl;
}

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
void Lecteur<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>::sauvegarderDonnees(string cheminFichier, const ComplexeCubique<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>&  _c)
{
//
     ofstream fichier(cheminFichier.c_str(),ios::trunc);
     int numLigne = 1;
     cout << endl << "Sauvegarde du Complexe dans le fichier : " << cheminFichier << endl << endl;
//
     fichier << T_DIMCOMPLEXE -1 << " " << T_DIMENSION;

     std::vector<Point<T_TYPE,T_DIMENSION>*> points;


//     for(//parcours des ICellule de dimension T_DIMCOMPLEXE)
//     {
//      //on récupère les points de la ICellule actuelle
//      _c.ComplexeCubique<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>::getIterator()->getCellI(0)->setListePointICellule(points);
//
//      //on écrit chaque point
//      for (unsigned i = 0; i < points.size(); i++)
//      {
//       for(unsigned j = 0; j < T_DIMENSION; j++)
//       {
//        fichier << points[i]->getCoordonnees(j);
//        if(j != T_DIMENSION-1) fichier << " ";
//       }
//      }
//
//      points.clear();
//     }

     //on écrit ensuite les identifiants des bords de chaque 1-cellule


}
//template <unsigned int T_DIMCOMPLEXE, typename T_TYPE, unsigned int T_DIMENSION>
//void Lecteur<T_DIMCOMPLEXE,T_TYPE,T_DIMENSION>::ecritureDim(string _chemin, vector<int> _dim)
//{
//    _dim.push_back()
//
//    if(T_DIMCOMPLEXE>0)
//    {
//           Lecteur<T_DIMCOMPLEXE-1,T_TYPE,T_DIMENSION>::ecritureDim(
//    }
//
//}



#endif // LECTEUR_H
