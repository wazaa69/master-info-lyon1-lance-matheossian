#include "module1.hh"

template <typename T>
T min(T e1,T e2)
{ return (e1<e2) ? e1 :e2; }

template int min<int>(int,int);
