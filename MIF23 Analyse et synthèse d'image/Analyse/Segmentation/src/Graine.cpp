#include "../include/Graine.h"

Graine::Graine(){}

Graine::Graine(const CvPoint& pt){setPtStart(pt);}

Graine::Graine(const int& x, const int& y){
    ptStart.x = x;
    ptStart.y = y;
}

Graine::Graine(const Graine& g){
    ptStart.x = g.ptStart.x;
    ptStart.y = g.ptStart.y;
}

Graine::~Graine(){}


const CvPoint& Graine::getPtStart() const {return ptStart;}
void Graine::setPtStart(const CvPoint& pt)
{
    ptStart.x = pt.x;
    ptStart.y = pt.y;
}
