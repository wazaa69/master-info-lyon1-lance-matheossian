#ifndef POINT_H
#define POINT_H

#include <vector>
#include <assert.h>

/// DECLARATION CLASSE POINT

static int compteur = 0;

template <typename T_TYPE, unsigned int T_DIMENSION>
class Point
{
    typedef Point<T_TYPE, T_DIMENSION> Self;

    public:

    /// CONSTRUCTEURS / DESTRUCTEURS
        Point();
        Point(const T_TYPE _coordonnees[T_DIMENSION]);
        Point(const Self &p);

        virtual ~Point(){ delete coordonnees;};

    /// SETTERS / GETTERS

        const T_TYPE getCoordonnees(const unsigned int _range) const;
        void setCoordonnees(const unsigned int _range, const T_TYPE _coordonnee);

    private:

        T_TYPE coordonnees[T_DIMENSION];

};

template <typename T_TYPE, unsigned int T_DIMENSION>
Point<T_TYPE, T_DIMENSION>::Point()
{
    for (unsigned int i = 0; i < T_DIMENSION; i++) coordonnees[i] = 0;
}

template <typename T_TYPE, unsigned int T_DIMENSION>
Point<T_TYPE,T_DIMENSION>::Point(const T_TYPE _coordonnees[T_DIMENSION])
{
    for (unsigned int i = 0; i < T_DIMENSION; i++) coordonnees[i] = _coordonnees[i];
}

template <typename T_TYPE, unsigned int T_DIMENSION>
Point<T_TYPE,T_DIMENSION>::Point(const Point<T_TYPE, T_DIMENSION> &_p)
{
    for (unsigned int i = 0; i < T_DIMENSION; i++) coordonnees[i] = _p.coordonnees[i];
    ++compteur;
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

