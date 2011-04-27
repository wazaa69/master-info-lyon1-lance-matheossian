//====================================================================
// Classe Tableau générique :
//   - sur le type de ses éléments
//   - et sur la taille de ses agrandissements
// Classe iterateur associée
//
//====================================================================
// Raphaelle Chaine  - Fevrier 2008
// Guillaume Damiand - Avril 2011
//====================================================================

#ifndef _TABLEAU_
#define _TABLEAU_

#include <iostream>
#include <algorithm>
#include <cassert>


//====================================================================
//  Declarations classe Tableau et fonctions amies
//   (pour eviter les messages d'erreurs lors des declarations d'amitie
//    dans la classe Tableau)
//====================================================================
template <class T,int agrandissement>
class Tableau;

template <class T,int agrandissement>
std::ostream  & operator << (std::ostream & os, const Tableau<T,agrandissement> & V);

template <class T,int agrandissement>
std::istream  & operator >> (std::istream & is, Tableau<T,agrandissement> & V);

template <class T,int agrandissement>
void swap(Tableau<T,agrandissement> &, Tableau<T,agrandissement> &);

//====================================================================
//  Definition classe Tableau
//====================================================================

template <class T,int agrandissement>
class Tableau {
public:

    class TabIterator
    {

        public:
            TabIterator()
            {

            }

            TabIterator(int _position,Tableau *_tab) : position(_position), tab(_tab)
            {
            }

            virtual ~TabIterator(){}
        protected:
        private:

            int position;
            Tableau* tab;
    };


  typedef T value_type;
  typedef Tableau<T,agrandissement> Self;

//-- Déclarations FONCTIONS AMIES
//-- Il faut bien penser à donner le nom complet des fonctions amies.
//-- En particulier, si ces fonctions sont instanciées à partir d'un
//-- template, penser à préciser les paramètres d'instanciation.

  // E/S de Tableaux
  friend std::ostream& operator<< <T,agrandissement>(std::ostream &, const Self &);
  friend std::istream& operator>> <T,agrandissement>(std::istream &, Self &);

 public:
  //Constructeurs et destructeur
  explicit Tableau (const int=0);
  Tableau (const int, const T & );
  Tableau (const Self &);
  ~Tableau ();

  //Acces aux elements d'un tableau
  T       & operator[](int); //Acces en lecture/ecriture
  const T & operator[](int) const; //Acces en ecriture

  // Addition de 2 Tableaux
  template <int agrandissement2>
  Self operator+ (const Tableau<T, agrandissement2> &);

  // Divers accesseurs
  int taille() const       {return nombre_element;}
  int capacity() const     {return capacite;}
  static int nb_instances(){return compteur;}

  friend void swap<T,agrandissement>(Self&, Self&);
  Tableau & operator=(const Self& );
  Tableau & ajoute(const T &);



    TabIterator begin() { return TabIterator(0,this);}
    TabIterator end() { return TabIterator(nombre_element,this);}
    bool operator!=(const TabIterator &t){ return !((this.tab == t.tab)&& (this.position == t.position));}


protected:
  void increase_capacity();

private :
  int nombre_element;
  int capacite;
  T * vect;
  static int compteur;
};

//====================================================================
//  Declarations fonctions amies classe iterator
//====================================================================

template <class T, int agrandissement>
bool operator != (const typename Tableau<T,agrandissement>::iterator & ,
		  const typename Tableau<T,agrandissement>::iterator & );

template <class T, int agrandissement>
bool operator == (const typename Tableau<T,agrandissement>::iterator & ,
		  const typename Tableau<T,agrandissement>::iterator & );

//====================================================================
//             Membre static template
//====================================================================
template <class T,int agrandissement>
int Tableau<T,agrandissement>::compteur=0;

//====================================================================
// Implantation des fonctions membres de Tableau
//====================================================================
template <class T,int agrandissement>
Tableau<T,agrandissement>::Tableau(const int  n) :
  nombre_element(n),
  capacite((n/agrandissement +1 )*agrandissement),
  vect(new T[capacite])
{
  ++compteur;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
Tableau<T,agrandissement>::Tableau(const int n,const T & val) :
  nombre_element(n),
  capacite((n/agrandissement+1)*agrandissement),
  vect(new T[capacite])
{
  for (int i = 0; i<nombre_element; ++i)
    vect[i]= val;
  ++compteur;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
Tableau<T,agrandissement>::Tableau(const Tableau<T,agrandissement> &V) :
  nombre_element(V.nombre_element),
  capacite(V.capacite),
  vect(new T[capacite])
{
  for (int i= 0; i<nombre_element; ++i)
    vect[i]=V.vect[i];
  ++compteur;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
Tableau<T,agrandissement>::~Tableau()
{
  --compteur;
  delete []vect;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
T &  Tableau<T,agrandissement>::operator[] (int i)
{
  assert((i < nombre_element)&&(i>=0));
  return vect[i];
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
const T &  Tableau<T,agrandissement>::operator[] (int i) const
{
  assert((i < nombre_element)&&(i >= 0));
  return vect[i];
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
void swap (Tableau<T,agrandissement> &t1,
	   Tableau<T,agrandissement> &t2)
{
  if ( &t1!=&t2 )
    {
      std::swap(t1.nombre_element, t2.nombre_element);
      std::swap(t1.capacite, t2.capacite);
      std::swap(t1.vect, t2.vect);
    }
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
Tableau<T,agrandissement> & Tableau<T,agrandissement>::operator =
(const Tableau<T,agrandissement> &  t)
{
  if ( &t != this)
    {
      Self copy(t);
      swap(*this, copy);
    }
  return *this;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
void Tableau<T,agrandissement>::increase_capacity()
{
  T * temp = new T[capacite+agrandissement];
  for (int i=0;i<nombre_element;++i)
    temp[i]=vect[i];
  delete []vect;
  vect=temp;
  capacite+=agrandissement;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
Tableau<T,agrandissement> & Tableau<T,agrandissement>::ajoute(const T & el)
{
  if(nombre_element == capacite) increase_capacity();

  assert(nombre_element<capacite);

  vect[nombre_element++]=el;
  return *this;
}

//====================================================================
// Implantation des fonctions externes
//====================================================================

template <class T,int agrandissement>
template <int agrandissement2>
Tableau<T,agrandissement>  Tableau<T,agrandissement>::operator+
(const Tableau<T, agrandissement2> & t2)
{
  const T * maxtab;
  int minnb;
  int nbelm ((nombre_element>t2.nombre_element)?
	     (maxtab=vect,minnb=t2.nombre_element,nombre_element):
	     (maxtab=t2.vect,minnb=nombre_element,t2.nombre_element));

  Tableau<T,agrandissement> R(nbelm);
  for(int i=0; i<minnb ; ++i)
    R.vect[i]= vect[i] + t2.vect[i];

  for(int i=minnb; i<nbelm ; ++i)
    R.vect[i]= maxtab[i];
  return R;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
std::ostream  & operator<< (std::ostream & os, const Tableau<T,agrandissement> & V)
{ os << std::endl << "[";
 for (int i=0; i< V.nombre_element; ++i)
      os << V.vect[i] << " ";
  os << "]" << std::endl;
  return os ;
}
//--------------------------------------------------------------------
template <class T,int agrandissement>
std::istream  & operator>> (std::istream &is, Tableau<T,agrandissement> & V)

{ std::cout << std::endl << "Debut saisie tableau\n";
  for (int i=0; i < V.nombre_element; ++i)
       {
	 std::cout << "[" << i<<"] :";
	 is >> V.vect[i];
	 std::cout << std::endl;
       }
  std::cout << "Fin saisie tableau\n";
  return is;
}


//==============================================================
#endif // _TABLEAU_
//==============================================================
