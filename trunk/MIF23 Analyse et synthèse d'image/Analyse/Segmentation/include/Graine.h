#ifndef GRAINE_H
#define GRAINE_H

#include <opencv/cv.h>
#include <opencv/highgui.h>

#include <iostream>

class Graine
{
    public:
        Graine();
        Graine(const CvPoint& pt);
        Graine(const int& x, const int& y);
        Graine(const Graine& g);
        virtual ~Graine();

        const CvPoint& getPtStart() const;
        void setPtStart(const CvPoint& pt);

    private:

        CvPoint ptStart;
};

#endif // GRAINE_H
