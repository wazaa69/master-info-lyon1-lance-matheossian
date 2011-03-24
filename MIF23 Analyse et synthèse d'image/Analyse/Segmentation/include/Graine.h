#ifndef GRAINE_H
#define GRAINE_H

#include <opencv/cv.h>
#include <opencv/highgui.h>

#include <iostream>

class Graine
{
    public:
        Graine(const unsigned int& _index, const CvPoint& pt);
        Graine(const unsigned int& _index, const int& x, const int& y);
        virtual ~Graine();

        const CvPoint& getPtStart();
        const unsigned int& getIndex();

    private:

        unsigned int index; /** le numéro de la graine */

        CvPoint ptStart;
};

#endif // GRAINE_H
