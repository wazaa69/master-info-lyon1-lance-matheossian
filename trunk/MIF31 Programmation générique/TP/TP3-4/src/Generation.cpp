#include "../include/Generation.h"

Generation::Generation(const unsigned int& _multiple) : multiple(_multiple), i(1)
{
}

Generation::~Generation()
{
}


unsigned int Generation::operator()()
{
    //std::cout << ( multiple * i) << " " ;
    int r = multiple *i;
    i++;
    return r;

}

