#ifndef ICELLULE_H
#define ICELLULE_H

#include <iostream>
#include <vector>

#include "Point.h"
#include <assert.h>

/**
* @class Classe I-Cellule :
* en dimension 0, une 0-cellule est un point,
* en dimension 1, une 1-cellule est un segment,
* en dimension 2, une 2-cellule est un carré,
* en dimension 3, une 3-cellule est un cube,
* en dimension 4, une 4-cellule est un tesseract.
*/

template <unsigned int DIMENSION, typename T , unsigned int DIMPOINT>
class ICellule
{
    public:

        typedef ICellule<DIMENSION, T, DIMPOINT> Self;

        ICellule(){};
        ICellule(const ICellule<DIMENSION, T, DIMPOINT> &_icellule){ bords = _icellule.bords;}
        /**
        * @param nouvBords les bords sont forcément des (i-1)-Cellules
        */
        ICellule(std::vector< ICellule<DIMENSION-1, T, DIMPOINT>* > nouvBords){}


        virtual ~ICellule(){}

        bool estValide(){ return bords.size() == 2 *DIMENSION;}

        void addBord(const ICellule<DIMENSION-1, T, DIMPOINT> &_icellule){bords.push_back(_icellule);}
        const ICellule* getBord(const unsigned int &i) const{return (i < bords.size())?bords[i]:NULL;}


    protected:

//         ICellule* operator[](int i){return getBord(i);}
         const ICellule* operator[](int i) const{return getBord(i);}
         ICellule& operator=(const Self& );


    private:

        /**
        * Bord :
        * un segment à 2 sommets (2 bords)
        * un carré à 4 segments (4 bords)
        * un cube à 6 faces (6 bords)
        * Le nombre de bords = 2 * DIMENSION
        * les 0-cellules (sommets) contiendront juste un point associé au sommet (voir spécialisation)
        */
        std::vector< ICellule<DIMENSION-1, T, DIMPOINT> > bords;

        /**
        * @brief Ajoute un bord à la cellule
        * @param icellule
        */
//        void addBord(ICellule* icellule){bords.push_back(icellule);}


};

template <unsigned int DIMENSION, typename T , unsigned int DIMPOINT >
ICellule<DIMENSION, T, DIMPOINT> & ICellule<DIMENSION, T, DIMPOINT>::operator=(const ICellule<DIMENSION, T, DIMPOINT>& _c)
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
template <class T, unsigned int DIMPOINT>
class ICellule<0, T, DIMPOINT>
{
    public:

        /**
        * @param nouvSommet le sommet car on est dans une 0-Cellules
        */
        ICellule(){}
        ICellule(const Point<T, DIMPOINT> &p);
        virtual ~ICellule(){}

    private:

        Point<T, DIMPOINT> sommet; /** Un point contenant 3 données de type T.
                                    *  Exemple : 3 double pour représenter les coordonnées x, y et z */

        const Point<T, DIMPOINT>* getBord() const{return sommet;}
};

template <class T, unsigned int DIMPOINT>
ICellule<0, T, DIMPOINT>::ICellule(const Point<T, DIMPOINT> &p)
{
    sommet = new Point<T, DIMPOINT>(p);
}



#endif // ICELLULE_H
