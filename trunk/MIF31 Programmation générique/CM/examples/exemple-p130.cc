#include <iostream>
#include <cstdlib>

template <typename T>
class Complexe
{
public:
  Complexe(T r=T(),T i=T()) : re(r),im(i)
  {}
  
  const Complexe & f (const Complexe &);
  template <typename T2>
  friend Complexe<T2> operator+ (const Complexe<T2> &,
				 const Complexe<T2> &);
private:
  T re;
  T im;
};

template<typename T>
const Complexe<T> &
Complexe<T>::f (const Complexe & c)
{ std::cout<<"Complexe<T>::f"<<std::endl; }

template<typename T2>
Complexe<T2>
operator+ (const Complexe<T2> & c1, const Complexe<T2> & c2)
{
  return Complexe<T2>(c1.re+c2.re, c1.im+c2.im);
}


int main()
{
  Complexe<int> c1(1,0),c2(2,2);
  c1.f(c1+c2);
  exit(EXIT_SUCCESS);
}
