#ifndef ICELLULE_H
#define ICELLULE_H

#include <iostream>
#include <vector>

#include "Point.h"
#include <assert.h>

static int nbCellules = 0;
static int nbPoints = 0;

/// DECLARATION CLASSE POINT

class EnsembleCell
{
        public:
                virtual ~EnsembleCell() {}
                  int getDimension();
};

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION>
class ICellule : public EnsembleCell
{
    public:

        typedef ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> Self;
        typedef ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION> Bord;

        /// CONSTRUCTEURS / DESTRUCTEURS

        ICellule();
        ICellule(std::vector< ICellule*> &_bords);
//        ICellule(std::vector< ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>* > nouvBords){}

        virtual ~ICellule(){bords.clear();}

        /// GETTERS / SETTERS

        const std::vector<Bord*>* getBords() const { return &bords;}
        int getDimension() const {return T_DIMENSION;}
        int getNbCellulesBord() const {return bords.size();}

        bool estValide(const bool _init);

//        ICellule& operator=(const Self& );

    private:
        std::vector< Bord* > bords;


};

/// DECLARATION SPECIALISATION CLASSE POINT DE DIMENSION 0

template <typename T_TYPE, unsigned int T_DIMENSION>
class ICellule<0, T_TYPE, T_DIMENSION> : public EnsembleCell
{
    public:

        ICellule();
        ICellule(const Point<T_TYPE, T_DIMENSION> &p);
        virtual ~ICellule(){}

        const std::vector<ICellule<0, T_TYPE, T_DIMENSION>*>* getBords() const { return NULL;}
        int getNbCellulesBord() const {return 0;}

        const Point<T_TYPE, T_DIMENSION>& getPoint() const{return sommet;}
        bool estValide(const bool _init);

        int getDimension() const {return T_DIMENSION;}

    private:

        Point<T_TYPE, T_DIMENSION> *sommet;

};

/// IMPLEMENTATION FONCTIONS MEMBRES

// Constructeur défaut
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::ICellule()
{
    int facteur = 2;
    nbCellules++;
    std::cout << "Initialisation cellule n° " << nbCellules << std::endl;

    for(unsigned int i = 0; i < facteur* T_DIMCOMPLEXE; i++)
    {
        bords.push_back(new ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>());
    }
}
// Constructeur par vector
template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::ICellule(std::vector< ICellule*> &_bords)
{
    nbCellules++;
    std::cout << "Initialisation cellule n° " << nbCellules << std::endl;
    for(unsigned int i = 0; i < 2* T_DIMCOMPLEXE; i++)
    {
        bords.push_back(_bords[i]);
    }
}

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
bool ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::estValide(const bool _init)
{
    bool retour = _init;

    for(unsigned int i = 0; i < bords.size(); i++)
    {
        retour &= bords[i]->estValide(retour);
    }

    if ( bords.size() != T_DIMCOMPLEXE * 2) retour = false;
    std::cout << T_DIMCOMPLEXE << "-cellule validite: " <<  ( bords.size() == T_DIMCOMPLEXE * 2) << std::endl;

    return retour;
}

template <typename T_TYPE , unsigned int T_DIMENSION >
bool ICellule<0, T_TYPE, T_DIMENSION>::estValide(const bool _init)
{
    bool retour = _init;

    retour &= (sommet != NULL);

    std::cout << "0-cellule validite: " <<  (sommet != NULL) << std::endl;

    return retour;
}




//template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
//ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> & ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::operator=(const ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>& _c)
//{
//    this.bords = _c.bords;
//    return *this;
//}



// Constructeur par défaut spé 0
template <typename T_TYPE , unsigned int T_DIMENSION >
ICellule<0, T_TYPE, T_DIMENSION>::ICellule()
{
    nbPoints++;
    std::cout << "Initialisation point n° " << nbPoints << std::endl;
    sommet = new Point<T_TYPE, T_DIMENSION>();
}


// Constructeur par point spé 0
template <typename T_TYPE, unsigned int T_DIMENSION>
ICellule<0, T_TYPE, T_DIMENSION>::ICellule(const Point<T_TYPE, T_DIMENSION> &p)
{
    nbPoints++;
    std::cout << "Initialisation point n° " << nbPoints << std::endl;
    sommet = new Point<T_TYPE, T_DIMENSION>(p);
}



#endif // ICELLULE_H
