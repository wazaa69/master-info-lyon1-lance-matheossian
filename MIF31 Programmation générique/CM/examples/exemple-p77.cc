#include <iostream>
#include <cstdlib>

class Employe
{
public:
  virtual void f1()
  { std::cout<<"Employe::f1()"<<std::endl; }
  virtual void f2(int i=0)
  { std::cout<<"Employe::f2(int)"<<std::endl; }
  virtual void f3(int)
  { std::cout<<"Employe::f3(int)"<<std::endl; }
};

class Cadre: public Employe
{
private :
  void f1()
  { std::cout<<"Cadre::f1"<<std::endl; }
public:
  virtual void f2(int)
  { std::cout<<"Cadre::f2(int)"<<std::endl; }
  virtual void f3(int)
  { std::cout<<"Cadre::f3(int)"<<std::endl; }
  virtual void f3(char)
  { std::cout<<"Cadre::f3(char)"<<std::endl; }  
};
  
int main()
{
  Employe* e=new Cadre;
  e->f1();
  e->f2();
  e->f3('a');
  
  Cadre* c=new Cadre;
  // c->f1();
  // c->f2();
  c->f3('a');
}
