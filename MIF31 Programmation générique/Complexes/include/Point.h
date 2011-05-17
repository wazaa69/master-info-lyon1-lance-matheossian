#ifndef POINT_H
#define POINT_H

#include <vector>
#include <assert.h>


static int numeroPoint = 0;

/// DECLARATION CLASSE POINT #############################################################################

/**
* @class Un point appartient à une 0-Cellule, elle est templatée par :
*       <ul>
*           <li>son type : int, double, ... </li>
*           <li>sa dimension : 3D (x,y,z), 2D(x,y), ...</li>
*       </ul>
*/
template <typename T_TYPE, unsigned int T_DIMENSION>
class Point
{
    typedef Point<T_TYPE, T_DIMENSION> Self;

    public:

    /// CONSTRUCTEURS / DESTRUCTEURS

        /** @brief Initialisation simple (mise à 0 des coordonnées) */
        Point();

        /**
        * @brief Initialisation de chaque coordonnée
        * @param _coordonnees les coordonnées à conserver
        */
        Point(const T_TYPE _coordonnees[T_DIMENSION]);

        /**
        * @brief Initialisation à partir d'un point existant
        * @param p un point templpaté par les mêmes données que la classe acuelle
        */
        Point(const Self &p);

        virtual ~Point(){ delete coordonnees;};

    /// SETTERS / GETTERS

        void setCoordonnees(const unsigned int _range, const T_TYPE _coordonnee);
        unsigned int getNumPoint()const{return numPoint;}
        const T_TYPE getCoordonnees(const unsigned int _range) const;

    private:

        unsigned int numPoint; /** identifiant unique du point */
        T_TYPE coordonnees[T_DIMENSION]; /** le tableau qui conserve les coordonnées (ex en 3D : x,y,z) */

};

/// IMPLEMENTATION FONCTIONS MEMBRES ######################################################################


template <typename T_TYPE, unsigned int T_DIMENSION>
Point<T_TYPE, T_DIMENSION>::Point()
{
    for (unsigned int i = 0; i < T_DIMENSION; i++) coordonnees[i] = 0;
}

template <typename T_TYPE, unsigned int T_DIMENSION>
Point<T_TYPE,T_DIMENSION>::Point(const T_TYPE _coordonnees[T_DIMENSION])
{
    for (unsigned int i = 0; i < T_DIMENSION; i++) coordonnees[i] = _coordonnees[i];
    numPoint = numeroPoint;
    std::cout << "Init point num " << numPoint << " de coordonnes " ;
    for(unsigned int j = 0; j < T_DIMENSION; j++) std::cout << coordonnees[j] << " ";

    std::cout << std::endl;
    ++numeroPoint;
}

template <typename T_TYPE, unsigned int T_DIMENSION>
Point<T_TYPE,T_DIMENSION>::Point(const Point<T_TYPE, T_DIMENSION> &_p)
{
    for (unsigned int i = 0; i < T_DIMENSION; i++) coordonnees[i] = _p.coordonnees[i];
    numPoint = _p.numPoint;
}

template <typename T_TYPE, unsigned int T_DIMENSION>
const T_TYPE Point<T_TYPE,T_DIMENSION>::getCoordonnees(const unsigned int _range) const
{
    assert(_range >= 0 && _range < T_DIMENSION);
    return coordonnees[_range];
}

template <typename T_TYPE, unsigned int T_DIMENSION>
void Point<T_TYPE,T_DIMENSION>::setCoordonnees(const unsigned int _range, const T_TYPE _coordonnee)
{
    assert(_range >= 0 && _range < T_DIMENSION);
    coordonnees[_range] = _coordonnee;
}


#endif // POINT_H

