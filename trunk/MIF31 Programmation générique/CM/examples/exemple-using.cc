#include <iostream>
#include <cstdlib>

class Employe
{
private:
  virtual void f1()
  { std::cout<<"Employe::f1()"<<std::endl; }
protected:
  virtual void f2()
  { std::cout<<"Employe::f2()"<<std::endl; }
public:
  virtual void f3()
  { std::cout<<"Employe::f3()"<<std::endl; }
};

class Cadre: public Employe
{
public:
  //  using Employe::f1;
  using Employe::f2;
  using Employe::f3;
};

class Cadre2: private Employe
{
public:
  //  using Employe::f1;
  using Employe::f2;
  using Employe::f3;
};

int main()
{
  Employe e;
  //  e.f2();
  Cadre c;
  c.f2();
  Cadre2 c2;
  c2.f2();
}
