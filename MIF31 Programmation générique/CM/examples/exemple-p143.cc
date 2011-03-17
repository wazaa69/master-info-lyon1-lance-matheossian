#include <cstdlib>

class Exo
{public:
  void non_stat() {}
  static void stat1(Exo &);
  static void stat2() {}
  // static void stat_const() const;
private:
  static int classe_var;
  int instance_var;
};

void Exo::stat1(Exo & arg)
{
  //instance_var=1;
  classe_var=2;
  arg.instance_var=3;
  // this->instance_var=4;
  stat2();
  // non_stat();
  arg.stat2();
  arg.non_stat();
}

int Exo::classe_var;

int main()
{
  Exo e; 
  e.stat1(e);
  return (EXIT_SUCCESS);
}
