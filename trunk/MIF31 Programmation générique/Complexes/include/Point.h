#ifndef POINT_H
#define POINT_H

#include <vector>

template <class T, unsigned int DIMENSION>
class Point
{
    public:
        Point();
        virtual ~Point();

        const T getCoordonnees(const unsigned int i) const{}
        void setCoordonnees(const unsigned int i, const T& coordonne);

    protected:


    private:

        std::vector<T> coordonnees;
};

#endif // POINT_H

