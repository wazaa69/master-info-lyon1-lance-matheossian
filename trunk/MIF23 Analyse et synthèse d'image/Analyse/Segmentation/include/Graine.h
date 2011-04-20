#ifndef GRAINE_H
#define GRAINE_H

#include <opencv/cv.h>
#include <opencv/highgui.h>

/**
* @class Permet de stocker les coordonnées de la graine
* Elle n'a pas plus d'attribut pour le moment.
*/
class Graine
{
    public:
        Graine();
        Graine(const CvPoint& pt);
        Graine(const int x, const int y);
        Graine(const Graine& g);
        virtual ~Graine();

        const CvPoint& getPtStart() const;
        void setPtStart(const CvPoint& pt);

    private:

        CvPoint ptStart;
};

#endif // GRAINE_H
