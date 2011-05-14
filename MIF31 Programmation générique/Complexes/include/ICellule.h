#ifndef ICELLULE_H
#define ICELLULE_H

#include <iostream>
#include <vector>

#include "Point.h"
#include <assert.h>

static int nbCellules = 0;
static int nbPoints = 0;

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION>
class ICellule
{
    public:

        typedef ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> Self;
        typedef ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION> Bord;

        /// CONSTRUCTEURS / DESTRUCTEURS

        ICellule();
        ICellule(const ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> &_icellule){ bords = _icellule.bords;}
        ICellule(std::vector< ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>* > nouvBords){}

        virtual ~ICellule(){bords.clear();}

        /// GETTERS / SETTERS

        ICellule& getBord(const unsigned int _range) const;
        void addBord(const Bord &_icellule);

        /// METHODES

        bool estValide();


    protected:

//         ICellule* operator[](int i){return getBord(i);}
        ICellule* operator[](int i) const{return getBord(i);}
        ICellule& operator=(const Self& );

    private:

        std::vector< Bord* > bords;
//        std::vector< ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>* > bords;
//        void addBord(ICellule* icellule){bords.push_back(icellule);}
};

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::ICellule()
{
    nbCellules++;
    std::cout << "Initialisation cellule n° " << nbCellules << std::endl;
    for(unsigned int i = 0; i < 2* T_DIMCOMPLEXE; i++)
    {
        bords.push_back(new ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION>());
    }

}


template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> & ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::getBord(const unsigned int _range) const
{
    assert(bords.size()>_range);
    return bords[_range];
}

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
void ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::addBord(const ICellule<T_DIMCOMPLEXE-1, T_TYPE, T_DIMENSION> &_icellule)
{
//  if(bords.size() < 2 * T_DIMCOMPLEXE) {bords.push_back(_icellule);}

}

template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
bool ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::estValide()
{
    if ( bords.size() != 2 * T_DIMENSION *( T_DIMENSION- 1 )) return false;
    return true;
}






template <unsigned int T_DIMCOMPLEXE, typename T_TYPE , unsigned int T_DIMENSION >
ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION> & ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>::operator=(const ICellule<T_DIMCOMPLEXE, T_TYPE, T_DIMENSION>& _c)
{
    this.bords = _c.bords;
    return *this;
}

//  Tableau & operator=(const Self& );

//template <class T,int agrandissement>
//Tableau<T,agrandissement> & Tableau<T,agrandissement>::operator =
//(const Tableau<T,agrandissement> &  t)
//{
//  if ( &t != this)
//    {
//      Self copy(t);
//      swap(*this, copy);
//    }
//  return *this;
//}


//Spécialisation : on a une 0-Cellule
template <typename T_TYPE, unsigned int T_DIMENSION>
class ICellule<0, T_TYPE, T_DIMENSION>
{
    public:

        ICellule();
        ICellule(const Point<T_TYPE, T_DIMENSION> &p);
        virtual ~ICellule(){}

    private:

        Point<T_TYPE, T_DIMENSION> *sommet;
        const Point<T_TYPE, T_DIMENSION>* getBord() const{return sommet;}
};

template <typename T_TYPE , unsigned int T_DIMENSION >
ICellule<0, T_TYPE, T_DIMENSION>::ICellule()
{
    nbPoints++;
    std::cout << "Initialisation point n° " << nbPoints << std::endl;
    sommet = new Point<T_TYPE, T_DIMENSION>();
}



template <typename T_TYPE, unsigned int T_DIMENSION>
ICellule<0, T_TYPE, T_DIMENSION>::ICellule(const Point<T_TYPE, T_DIMENSION> &p)
{
    sommet = new Point<T_TYPE, T_DIMENSION>(p);
}



#endif // ICELLULE_H
