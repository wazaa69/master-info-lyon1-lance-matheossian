#include <iostream>
#include <cstdlib>
#include <cstring>

class Employe
{
public:
  Employe()
  { specifique(); }

  virtual void specifique()
  { std::strncpy(type,"Employe",49); }

  void affiche()
  { std::cout<<type<<std::endl; }

protected:
  char type[50];  
};

class Cadre: public Employe
{
public :
  Cadre() : Employe()
  {}
  
  void specifique()
  { std::strncpy(type,"Cadre",49); }
};
  
int main()
{
  Employe e; Cadre c;
  Employe* ade=new Cadre;
  Cadre* adc=new Cadre;

  e.affiche();
  c.affiche();
  ade->affiche();
  adc->affiche();
  
  exit(EXIT_SUCCESS);
}
