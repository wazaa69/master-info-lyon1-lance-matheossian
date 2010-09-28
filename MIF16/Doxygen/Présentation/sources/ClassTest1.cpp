


#include "ClassTest1.h"

ClassTest1::ClassTest1()
{

}


ClassTest1(int arg1)
{
  attribut1 = arg1;
}


ClassTest1::~ClassTest1()
{

}


float ClassTest1::testDivision()
{
    return MACRO_1/MACRO_2;
}
