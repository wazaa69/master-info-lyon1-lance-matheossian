#include "TypeArray.hpp"

using namespace std;

TypeArray::TypeArray()
{
    type = new string("Array");
}

TypeArray::TypeArray(TypeArray* _ta)
{
    type = new string("Array");
	typeArray = _ta->getTypeTab();
	intervalArray = _ta->getInterval();
}

TypeArray::TypeArray(TypeInterval* _interval, Type* _type)
{
    type = new string("Array");
	typeArray = _type;
	intervalArray = _interval;
	
}

Type* TypeArray::getTypeTab(){ return typeArray;}

TypeInterval* TypeArray::getInterval(){ return intervalArray;}




TypeArray::~TypeArray(){}

