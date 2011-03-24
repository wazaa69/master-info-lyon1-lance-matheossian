#include "../include/Graine.h"

Graine::Graine(const unsigned int& _index, const CvPoint& pt): index(_index){
    ptStart.x = pt.x;
    ptStart.y = pt.y;
}
Graine::Graine(const unsigned int& _index, const int& x, const int& y): index(_index){
    ptStart.x = x;
    ptStart.y = y;
}
Graine::~Graine(){}


const CvPoint& Graine::getPtStart(){return ptStart;}
const unsigned int& Graine::getIndex(){return index;}
