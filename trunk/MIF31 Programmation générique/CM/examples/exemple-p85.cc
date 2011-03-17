#include <iostream>
#include <cstdlib>

class Employe
{
public:
  Employe(int n): num(n)
  {}

  virtual void affiche()
  { std::cout<<"dynamique Employe::affiche"<<std::endl; }

  ~Employe()
  { std::cout<<"~Employe"<<std::endl; }
  
private:
  int num;
};

class Cadre: public Employe
{
public :
  Cadre(int n, int e) : Employe(n), echelon(e)
  {}
  
  virtual void affiche()
  { std::cout<<"dynamique Cadre::affiche"<<std::endl; }

  ~Cadre()
  { std::cout<<"~Cadre"<<std::endl; }
  
private:
  int echelon;
};
  
int main()
{
  Employe* e=new Cadre(1,10);
  delete e;
  exit(EXIT_SUCCESS);
}
