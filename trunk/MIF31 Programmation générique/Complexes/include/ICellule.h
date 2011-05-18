#ifndef ICELLULE_H
#define ICELLULE_H

#include <iostream>
#include <vector>

#include "Point.h"
#include <assert.h>

static int numeroCellule = 0;

/// DECLARATION CLASSE ICELLULE ###########################################################################

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION>
class ICellule
{
    public:

        typedef ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> Self;
        typedef ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION> Bord;

        /// CONSTRUCTEURS / DESTRUCTEURS

        ICellule(){}
        /** @brief Initialisation des bords */
        ICellule(std::vector< Bord*> &_bords);
        virtual ~ICellule(){bords.clear();}

        /// GETTERS / SETTERS

        const std::vector<Bord*>* getBords() const { return &bords;}
//        Bord* getBord(const unsigned int _position) const { return bords[_position};}
        unsigned int getDimension() const {return T_DIMCOMPLEXE;}
        unsigned int getNbCellulesBord() const {return bords.size();}
        unsigned int getNumCellule() const { return numCellule;}

        void setListePointICellule(std::vector<Point<T_TYPE,T_DIMENSION>*> &_vp);

    private:

        std::vector< Bord* > bords; /** les bords sont d'1 dimension inférieur à la ICellule actuelle */
        unsigned int numCellule; /** identifiant unique de la cellule */
};

/// DECLARATION CLASSE ICELLULE SPECIALISATION T_DIMENSION 0 ###############################################

template <typename T_TYPE, unsigned int T_DIMENSION>
class ICellule<0, T_TYPE, T_DIMENSION>
{
    public:

        typedef ICellule<0, T_TYPE, T_DIMENSION> Self;
        typedef Point<T_TYPE, T_DIMENSION> Bord;

        /// CONSTRUCTEURS / DESTRUCTEURS

        ICellule(){}
        ICellule(const Point<T_TYPE, T_DIMENSION> &_p);
        virtual ~ICellule(){delete(sommet);}

        /// GETTERS / SETTERS

        const std::vector<Self *>* getBords() const { return NULL;}
        const Bord& getPoint() const{return sommet;}
        bool estInitPoint()const { if(sommet != NULL) return true; else return false;}
        unsigned int getDimension() const {return T_DIMENSION;}
        unsigned int getNumCellule() const { return numCellule;}
        void setListePointICellule(std::vector<Point<T_TYPE,T_DIMENSION>*> &_vp);


    private:

        Point<T_TYPE, T_DIMENSION> *sommet;
        unsigned int numCellule;
};

/// IMPLEMENTATION FONCTIONS MEMBRES ######################################################################

// Constructeur par vector
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::ICellule(std::vector< Bord*> &_bords)
{
    if(_bords.size() == 2 * T_DIMCOMPLEXE)
    {
        numCellule = numeroCellule;
        numeroCellule++;

        std::cout << "Init cellule num" << numCellule << " de dim " << T_DIMCOMPLEXE << " de bord " << T_DIMCOMPLEXE-1 << "-cell num";
        for(unsigned int i = 0; i < _bords.size(); i ++) { std::cout << _bords[i]->getNumCellule() << " ";
            bords.push_back(_bords[i]);
        }
        std::cout << std::endl;

    }
    else
    {
        std::cout << "Mauvais nombre de (i-1)cellules en argument." << std::endl;
    }
}

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
void ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::setListePointICellule(std::vector<Point<T_TYPE,T_DIMENSION>*> &_vp)
{
    for(unsigned int i = 0; i < bords.size(); i++)
    {
        bords[i]->setListePointICellule(_vp);
    }
}


// Constructeur par point spé 0
template <typename T_TYPE, unsigned int T_DIMENSION>
ICellule<0, T_TYPE, T_DIMENSION>::ICellule(const Point<T_TYPE, T_DIMENSION> &_p)
{
    numCellule = numeroCellule;
    numeroCellule++;

    std::cout << "Init cellule num" << numCellule << " de dim 0 de bord  point num"  << _p.getNumPoint() <<std::endl;
    sommet = new Point<T_TYPE, T_DIMENSION>(_p);
}


template < typename T_TYPE , unsigned int T_DIMENSION >
void ICellule<0, T_TYPE, T_DIMENSION>::setListePointICellule(std::vector<Point<T_TYPE,T_DIMENSION>*> &_vp)
{
    bool dejaPresent = false;

    for(unsigned int i = 0; i < _vp.size(); i++)
    {
        if(sommet->getNumPoint() == _vp[i]->getNumPoint()) dejaPresent = true;
    }
    if(!dejaPresent) {_vp.push_back(sommet);  std::cout << "Cellule: " << getNumCellule() << " -> ajout du point " << sommet->getNumPoint() << std::endl;}


}


#endif // ICELLULE_H
