#include "TypeInterval.hpp"


using namespace std;

TypeInterval::TypeInterval()
{
	type = new string("Interval");
	
}

TypeInterval::~TypeInterval(){}

int TypeInterval::getDebut(){ return debut;}

int TypeInterval::getFin(){ return fin;}

TypeInterval::TypeInterval(int _debut, int _fin)
{
	
	debut = _debut;
	fin = _fin;
	type = new string("Interval");

}
