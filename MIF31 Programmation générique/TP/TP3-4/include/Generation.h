#ifndef GENERATION_H
#define GENERATION_H

#include <iostream>

class Generation
{
    public:
        Generation(const unsigned int& _multiple);
        virtual ~Generation();
        unsigned int operator()();

    protected:

    unsigned int multiple;

    private:
    unsigned int i;


};

#endif // GENERATION_H
