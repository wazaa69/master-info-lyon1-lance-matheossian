#ifndef POINT_H
#define POINT_H

#include <vector>

template <typename T, unsigned int DIMENSION>
class Point
{
    public:

        Point(){}
        Point( double &_coordonnees);

        virtual ~Point(){};

        const T getCoordonnees(const unsigned int i) const{ return coordonnees[i];}
        void setCoordonnees(const unsigned int i, const T& coordonnee) {coordonnees[i] = coordonnee;}

    protected:


    private:

        T* coordonnees;

};

template <typename T, unsigned int DIMENSION>
Point<T,DIMENSION>::Point(double &_coordonnees)
{
    coordonnees = &_coordonnees;
}

#endif // POINT_H

