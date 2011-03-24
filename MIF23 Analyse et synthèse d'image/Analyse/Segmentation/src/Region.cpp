#include "../include/Region.h"

std::vector<int> Region::listeIndex;

Region::Region(const int& _index, Graine& _graine){
    listeIndex.push_back(_index);
    listeGraines.push_back(_graine);
}

Region::~Region(){}

Graine& Region::getGraine(){return listeGraines[0];}
